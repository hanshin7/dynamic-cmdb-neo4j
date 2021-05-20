package top.hanshin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.hanshin.constant.ErrorCode;
import top.hanshin.constant.SysConstance;
import top.hanshin.exception.CommonException;
import top.hanshin.model.node.Ci;
import top.hanshin.model.node.CiBusiModel;
import top.hanshin.model.relation.HaveDynamicRel;
import top.hanshin.repository.node.CiBusiModelRepository;
import top.hanshin.repository.node.CiRepository;
import top.hanshin.repository.relation.HaveDynamicRelRepository;
import top.hanshin.service.ICiBusiModelService;

import java.util.*;

@Service
public class CiBusiModelService implements ICiBusiModelService {

    @Autowired
    private CiBusiModelRepository ciBusiModelRepository;
    @Autowired
    private CiRepository ciRepository;
    @Autowired
    private HaveDynamicRelRepository haveDynamicRelRepository;

    @Transactional
    @Override
    public Map<String, Object> saveNodeAndRelation(Map<String, Object> insertDto) {
        String ciKey = (String) insertDto.get(SysConstance.CI_KEY);

        Optional<Ci> ciOptional = ciRepository.findByCode(ciKey);
        if(!ciOptional.isPresent()){
            throw new CommonException(ErrorCode.BAD_REQUEST.getCode(),"CI节点不存在");
        }
        Map<String, Object> fields = getBusiProp(ciOptional.get(), insertDto);

        CiBusiModel busiModel = createBusiModel(ciKey);
        //方式1 node中字段会有前缀
        //busiModel.setProperties(fields);
        ciBusiModelRepository.save(busiModel);
        //方式2 直接map方式添加
        ciBusiModelRepository.setOrUpdateProp(busiModel.getId(), fields);

        //建立节点间关系
        haveDynamicRelRepository.save(new HaveDynamicRel(ciOptional.get(), busiModel, new Date()));

        return ciBusiModelRepository.getNodeMap(busiModel.getId());
    }

    @Override
    public Map<String, Object> detail(String id) {
        return ciBusiModelRepository.getNodeMap(id);
    }

    public List<Map<String, Object>> list(Pageable pageable) {

        return null;
    }

    @Override
    public void update(Map<String, Object> kvMap) {
        String id = (String) kvMap.get("id");
        kvMap.remove("id");
        ciBusiModelRepository.setOrUpdateProp(id, kvMap);
    }

    @Override
    public void delete(String id) {
        ciBusiModelRepository.deleteNodeAndRelation(id);
    }

    private Map<String, Object> getBusiProp(Ci ci, Map<String, Object> insertDto) {
        Map<String, Object> fieldMap = new HashMap<>();

        //props需扩展加入更多配置信息，此处简化
        String propStr =ci.getProps();
        Map<String, Object> props = null;
        try {
            props = new ObjectMapper().readValue(propStr, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //取字段列
        if(props != null && props.containsKey(SysConstance.CI_PROP_FIELD)){
            List<String> fields = (List<String>) props.get(SysConstance.CI_PROP_FIELD);
            //如果dto传入了该字段则取值放入map
            fields.forEach(key -> {
                if(insertDto.containsKey(key)){
                    fieldMap.put(key, insertDto.get(key));
                }
            });

        }

        return fieldMap;
    }


    private CiBusiModel createBusiModel(String ciKey) {
        CiBusiModel busiModel = new CiBusiModel();
        busiModel.setCiKey(ciKey);

        Set<String> labels = new HashSet<>();
        labels.add(ciKey);
        busiModel.setLabels(labels);

        return busiModel;
    }

}
