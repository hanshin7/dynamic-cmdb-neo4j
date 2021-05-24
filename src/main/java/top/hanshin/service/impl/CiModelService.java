package top.hanshin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.hanshin.constant.ErrorCode;
import top.hanshin.exception.CommonException;
import top.hanshin.model.CiModelVO;
import top.hanshin.model.node.CiModel;
import top.hanshin.model.relation.HaveDynamicRel;
import top.hanshin.repository.node.CiModelRepository;
import top.hanshin.repository.relation.HaveDynamicRelRepository;
import top.hanshin.service.ICiModelService;
import top.hanshin.util.TreeUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CiModelService implements ICiModelService {

    @Autowired
    private CiModelRepository ciModelRepository;
    @Autowired
    private HaveDynamicRelRepository haveDynamicRelRepository;

    @Override
    public List<CiModelVO> getTree() {
        List<CiModel> nodes = ciModelRepository.tree();
        return TreeUtil.builTree(nodes);
    }

    public void saveNodeAndRelation(CiModel ciModel) {
        ciModelRepository.save(ciModel);
        if(!StringUtils.isEmpty(ciModel.getParentCode())){
            Optional<CiModel> parentNode = ciModelRepository.findByCode(ciModel.getParentCode());
            parentNode.ifPresent(parentCi -> {
                HaveDynamicRel<CiModel, CiModel> dynamicRel = new HaveDynamicRel<>(parentCi, ciModel, new Date());
                haveDynamicRelRepository.save(dynamicRel);
            });
        }
    }

    public boolean update(CiModel ciModel){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
           map = objectMapper.readValue(objectMapper.writeValueAsString(ciModel), Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        map.entrySet().removeIf(entry -> entry.getValue() == null);
        ciModelRepository.setOrUpdateProp(ciModel.getId(), map);

        return true;
    }

    @Transactional
    public void delete(String ciId){

    }

    @Override
    public CiModel detail(String id) {

        Optional<CiModel> optionalCi = ciModelRepository.findById(id);
        if(optionalCi.isPresent()){
            return optionalCi.get();
        }
        return null;
    }

    @Override
    public Map<String, Object> getModelProps(String id) {
        Optional<CiModel> optionalCi = ciModelRepository.findById(id);
        if(!optionalCi.isPresent()){
            throw new CommonException(ErrorCode.BUSINESS_QUERY_FAILED);
        }

        //Map<String, Object> props = getCiProps(optionalCi.get());
        //return (List<Map<String, String>>) props.get(SysConstance.CI_PROP_RELATION);
        return getCiProps(optionalCi.get());
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

}
