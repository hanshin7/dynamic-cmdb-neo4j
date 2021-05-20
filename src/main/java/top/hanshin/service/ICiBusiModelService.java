package top.hanshin.service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ICiBusiModelService {

    Map<String, Object> saveNodeAndRelation(Map<String,Object> insertDto);

    Map<String, Object> detail(String id);

    List<Map<String, Object>> list(Pageable pageable);

    void update(Map<String,Object> kvMap);

    void delete(String id);

}
