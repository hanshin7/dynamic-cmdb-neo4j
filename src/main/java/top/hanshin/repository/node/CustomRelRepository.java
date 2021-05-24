package top.hanshin.repository.node;

import org.springframework.data.domain.Page;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import top.hanshin.model.PageDTO;
import top.hanshin.model.node.CustomRel;

import java.util.Map;


/**
 * create by hanshin on 2021/5/24
 */
public interface CustomRelRepository extends Neo4jRepository<CustomRel, String> {
    @Query("WITH {kvMap} as kv MATCH (n:CustomRel{id:{id}}) SET n += kv")
    void setOrUpdateProp(@Param("id") String id, @Param("kvMap") Map<String,Object> kvMap);
}
