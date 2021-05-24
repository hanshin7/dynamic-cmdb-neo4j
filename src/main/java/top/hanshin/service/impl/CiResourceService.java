package top.hanshin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.hanshin.constant.ErrorCode;
import top.hanshin.constant.SysConstance;
import top.hanshin.exception.CommonException;
import top.hanshin.model.PageDTO;
import top.hanshin.model.node.CiModel;
import top.hanshin.model.node.CiResource;
import top.hanshin.model.node.CustomRel;
import top.hanshin.model.relation.HaveDynamicRel;
import top.hanshin.repository.node.CiResourceRepository;
import top.hanshin.repository.node.CiModelRepository;
import top.hanshin.repository.node.CustomRelRepository;
import top.hanshin.repository.relation.HaveDynamicRelRepository;
import top.hanshin.service.ICiResourceService;
import top.hanshin.util.CqlExecUtils;

import java.util.*;

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
        return ciResourceRepository.getNodeMap(id);
    }

    public Page<CiResource> list(PageDTO dto) {
        Pageable pageable = PageRequest.of(dto.getCurrent(), dto.getSize());
        return ciResourceRepository.findAllByCiKey(dto.getCiKey(), pageable);
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
        Optional<CustomRel> optionalCustomRel = customRelRepository.findById(relId);
        if(!optionalCustomRel.isPresent()){
            throw new CommonException(ErrorCode.BAD_REQUEST.getCode(), "不存在relId资源");
        }
        return CqlExecUtils.createRel(sessionFactory, startId, endId, optionalCustomRel.get());
    }

    private Map<String, Object> getBusiProp(Map<String, Object> props, Map<String, Object> insertDto) {
        Map<String, Object> fieldMap = new HashMap<>();

        //取字段列
        if(props != null && props.containsKey(SysConstance.CI_PROP_FIELD)){
            List<Map<String, String>> fields = (List<Map<String, String>>)props.get(SysConstance.CI_PROP_FIELD);

            //如果dto传入了该字段则取值放入map
            fields.forEach(fieldDetailMap -> {
                String key = fieldDetailMap.get(SysConstance.CI_PROP_FIELD_KEY);
                String dataType = fieldDetailMap.get(SysConstance.CI_PROP_FIELD_DATATYPE);
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
        busiModel.setCiKey(SysConstance.CI_PREX + ciKey);

        Set<String> labels = new HashSet<>();
        labels.add(ciKey);
        busiModel.setLabels(labels);

        return busiModel;
    }

}
