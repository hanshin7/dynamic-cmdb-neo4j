package top.hanshin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.hanshin.constant.ErrorCode;
import top.hanshin.constant.SysConstance;
import top.hanshin.constant.ValueDataType;
import top.hanshin.exception.CommonException;
import top.hanshin.model.PageDTO;
import top.hanshin.model.node.CiModel;
import top.hanshin.model.node.CiResource;
import top.hanshin.model.node.CustomRel;
import top.hanshin.model.relation.HaveDynamicRel;
import top.hanshin.repository.node.CiResourceRepository;
import top.hanshin.repository.node.CiModelRepository;
import top.hanshin.repository.node.CustomRelRepository;
import top.hanshin.repository.relation.CiResourceRelationRepository;
import top.hanshin.repository.relation.HaveDynamicRelRepository;
import top.hanshin.service.ICiResourceService;
import top.hanshin.util.CqlExecUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CiResourceService implements ICiResourceService {

    @Autowired
    private CiResourceRepository ciResourceRepository;
    @Autowired
    private CiModelRepository ciModelRepository;
    @Autowired
    private HaveDynamicRelRepository haveDynamicRelRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CustomRelRepository customRelRepository;
    @Autowired
    CiResourceRelationRepository ciResourceRelationRepository;

    @Transactional
    @Override
    public Map<String, Object> saveNodeAndRelation(Map<String, Object> insertDto) {
        String ciKey = (String) insertDto.get(SysConstance.CI_KEY);

        Optional<CiModel> ciOptional = ciModelRepository.findByCode(ciKey);
        if(!ciOptional.isPresent()){
            throw new CommonException(ErrorCode.BAD_REQUEST.getCode(),"CI节点不存在");
        }

        CiResource busiModel = createBusiModel(ciKey);
        //方式1 node中字段会有前缀
        //busiModel.setProperties(fields);
        ciResourceRepository.save(busiModel);

        Map<String, Object> props = getCiProps(ciOptional.get());
        Map<String, Object> fields = getBusiProp(props, insertDto);

        //方式2 直接map方式添加
        ciResourceRepository.setOrUpdateProp(busiModel.getId(), fields);

        //获取模型配置的关系
        //List<Map<String, String>> relations = (List<Map<String, String>>) props.get(SysConstance.CI_PROP_RELATION);
        //默认的节点和CI关系
        haveDynamicRelRepository.save(new HaveDynamicRel(ciOptional.get(), busiModel, new Date()));

        return ciResourceRepository.getNodeMap(busiModel.getId());
    }

    private Map<String, Object> getCiProps(CiModel ciModel) {
        Map<String, Object> props = null;
        try {
            props = new ObjectMapper().readValue(ciModel.getProps(), Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return props;
    }

    @Override
    public Map<String, Object> detail(String id) {
        //ciResourceRepository.getNodeMap(id)不能被修改，重新创建map
        Map<String, Object> resultMap = new HashMap<>(ciResourceRepository.getNodeMap(id));
        //设置表单关联字段属性
        dealRelValue(resultMap);
        //查询节点关联关系
        queryRel(resultMap);
        return resultMap;
    }

    private void queryRel(Map<String, Object> resultMap) {
        List<Map<String, Object>> upRel = ciResourceRelationRepository.queryUpRel(resultMap.get(SysConstance.ID).toString());
        List<Map<String, Object>> downRel = ciResourceRelationRepository.queryDownRel(resultMap.get(SysConstance.ID).toString());
        resultMap.put("upRel", upRel);
        resultMap.put("downRel", downRel);
    }

    //rel数据类型 查询关联的值存放到字段上
    private void dealRelValue(Map<String, Object> resultMap) {
        Optional<CiModel> ciOptional = ciModelRepository.findByCode(resultMap.get(SysConstance.CI_KEY).toString());
        if(!ciOptional.isPresent()){
            throw new CommonException(ErrorCode.BAD_REQUEST.getCode(),"CI节点不存在");
        }

        Map<String, Object> props = getCiProps(ciOptional.get());
        List<Map<String, Object>> fields = (List<Map<String, Object>>) props.get(SysConstance.CI_PROP_FIELD);
        //数据类型为rel将根据 节点::id 和 relCol 查询实际的值
        fields.forEach(fieldDetailMap -> {
            String dataType = (String) fieldDetailMap.get(SysConstance.CI_PROP_FIELD_DATATYPE);
            if(ValueDataType.REL.toString().equals(dataType.toUpperCase())){
                String key = fieldDetailMap.get(SysConstance.CI_PROP_FIELD_KEY).toString();
                // val格式为 节点::id
                String val = (String) resultMap.get(key);
                if(!StringUtils.isEmpty(val) && val.contains(SysConstance.CHRA_DOUBLE_COLON)){
                    String node = val.split(SysConstance.CHRA_DOUBLE_COLON)[0];
                    String nodeId = val.split(SysConstance.CHRA_DOUBLE_COLON)[1];

                    Map<String, String> relPropMap = (Map<String, String>) fieldDetailMap.get(SysConstance.CI_PROP_RELPROP);
                    String col = relPropMap.get(SysConstance.CI_PROP_RELPROP_COL);
                    Object colVal = getRelCol(node, nodeId, col);
                    //resultMap.put(key, colVal);
                    resultMap.replace(key, colVal);
                }
            }
        });
    }

    private Object getRelCol(String node, String nodeId, String col) {
        String cql = "MATCH (n:" + node + "{id:'" + nodeId + "'}) RETURN properties(n) as n";
        Object r = CqlExecUtils.query(sessionFactory,cql);
        return r == null ? null : ((Map)((Map)r).get("n")).get(col);
    }

    public Page<Map<String, Object>> list(PageDTO dto) {
        Pageable pageable = PageRequest.of(dto.getCurrent(), dto.getSize());
        //Page<CiResource> page = ciResourceRepository.findAllByCiKey(dto.getCiKey(), pageable);
        List<Map<String, Object>> list = ciResourceRepository.findAllByCiKey(dto.getCiKey(), dto.getCurrent() * dto.getSize(), dto.getSize());

        List<Map<String, Object>> resultList = list.stream().map(map -> {
            //传入的map是Collection转换而来，不能被修改
            Map<String, Object> nmap = (Map<String, Object>) map.get("nmap");
            Map<String, Object> resultMap = new HashMap<>(nmap);
            //查询关联属性
            dealRelValue(resultMap);
            return resultMap;
        }).collect(Collectors.toList());


        return new PageImpl(resultList, pageable, resultList.size());
    }

    @Override
    public void update(Map<String, Object> kvMap) {
        String id = (String) kvMap.get("id");
        kvMap.remove("id");
        ciResourceRepository.setOrUpdateProp(id, kvMap);
    }

    @Override
    public void delete(String id) {
        ciResourceRepository.deleteNodeAndRelation(id);
    }

    @Override
    public boolean createRel(String startId, String endId, String relId) {
        //检查该资源模型是否配置了该关系
        Optional<CustomRel> optionalCustomRel = customRelRepository.findById(relId);
        if(!optionalCustomRel.isPresent()){
            throw new CommonException(ErrorCode.BAD_REQUEST.getCode(), "不存在relId资源");
        }
        return CqlExecUtils.createRel(sessionFactory, startId, endId, optionalCustomRel.get());
    }

    @Override
    public void deleteRel(String startId, String endId, String relName) {
        CqlExecUtils.deleteRel(sessionFactory, startId, endId, relName);
    }

    private Map<String, Object> getBusiProp(Map<String, Object> props, Map<String, Object> insertDto) {
        Map<String, Object> fieldMap = new HashMap<>();

        //取字段列
        if(props != null && props.containsKey(SysConstance.CI_PROP_FIELD)){
            List<Map<String, Object>> fields = (List<Map<String, Object>>)props.get(SysConstance.CI_PROP_FIELD);

            //如果dto传入了该字段则取值放入map
            fields.forEach(fieldDetailMap -> {
                String key = (String) fieldDetailMap.get(SysConstance.CI_PROP_FIELD_KEY);
                //String dataType = (String) fieldDetailMap.get(SysConstance.CI_PROP_FIELD_DATATYPE);
                if(insertDto.containsKey(key)){
                    fieldMap.put(key, insertDto.get(key));
                    //类型转换待处理...
                }
            });
        }

        return fieldMap;
    }


    private CiResource createBusiModel(String ciKey) {
        CiResource busiModel = new CiResource();
        busiModel.setCiKey(ciKey);

        Set<String> labels = new HashSet<>();
        labels.add(SysConstance.CI_PREX + ciKey);
        busiModel.setLabels(labels);

        return busiModel;
    }

}
