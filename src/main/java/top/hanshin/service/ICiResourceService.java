package top.hanshin.service;

import org.springframework.data.domain.Page;
import top.hanshin.model.PageDTO;
import top.hanshin.model.node.CiResource;

import java.util.Map;

public interface ICiResourceService {

    Map<String, Object> saveNodeAndRelation(Map<String,Object> insertDto);

    Map<String, Object> detail(String id);

    Page<Map<String, Object>> list(PageDTO dto);

    void update(Map<String,Object> kvMap);

    void delete(String id);

    boolean createRel(String startId, String endId, String relId);
    void deleteRel(String startId, String endId, String relName);

}
