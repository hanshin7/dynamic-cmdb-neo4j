package top.hanshin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.hanshin.constant.ErrorCode;
import top.hanshin.exception.CommonException;
import top.hanshin.model.PageDTO;
import top.hanshin.model.node.CustomRel;
import top.hanshin.repository.node.CustomRelRepository;
import top.hanshin.service.ICustomRelService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomRelService implements ICustomRelService {

    @Autowired
    private CustomRelRepository customRelRepository;

    @Override
    public Page<CustomRel> list(PageDTO dto) {
        Pageable pageable = PageRequest.of(dto.getCurrent(), dto.getSize());
        return customRelRepository.findAll(pageable);
    }

    @Override
    public CustomRel deatil(String id) {
        Optional<CustomRel> customRel = customRelRepository.findById(id);
        if(!customRel.isPresent()){
            throw new CommonException(ErrorCode.BUSINESS_QUERY_FAILED);
        }
        return customRel.get();
    }

    @Override
    public void create(CustomRel customRel) {
        customRelRepository.save(customRel);
    }

    @Override
    public void update(CustomRel customRel) {
        Map<String, Object> map = new HashMap<>();
        if(!StringUtils.isEmpty(customRel.getName())){
            map.put("name", customRel.getName());
        }
        if(!StringUtils.isEmpty(customRel.getToStartName())){
            map.put("toStartName", customRel.getToStartName());
        }
        if(!StringUtils.isEmpty(customRel.getToEndName())){
            map.put("toEndName", customRel.getToEndName());
        }
        customRelRepository.setOrUpdateProp(customRel.getId(), map);
    }

    @Override
    public void delete(String id) {
        CustomRel customRel = new CustomRel();
        customRel.setId(id);
        customRelRepository.delete(customRel);
    }
}
