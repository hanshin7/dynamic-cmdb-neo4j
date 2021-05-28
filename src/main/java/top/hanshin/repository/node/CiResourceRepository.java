package top.hanshin.repository.node;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import top.hanshin.model.node.CiResource;

import java.util.List;
import java.util.Map;

public interface CiResourceRepository extends Neo4jRepository<CiResource, String> {

    @Query("WITH {kvMap} as kv MATCH (n:CiResource {id:{id}}) SET n += kv")
    void setOrUpdateProp(@Param("id") String id, @Param("kvMap") Map<String,Object> kvMap);

    @Query("MATCH (n:CiResource{id:{id}}) return properties(n)")
    Map<String, Object> getNodeMap(@Param("id") String id);

    @Query("MATCH (n:CiResource{id:{id}})<-[r]-(:CI) delete r,n")
    void deleteNodeAndRelation(@Param("id") String id);

    Page<CiResource> findAllByCiKey(@Param("ciKey") String ciKey, Pageable pageable);

    @Query("MATCH (n:CiResource{ciKey:{ciKey}}) return properties(n) as nmap skip {cur} limit {size}")
    List<Map<String, Object>> findAllByCiKey(@Param("ciKey") String ciKey, int cur, int size);

}
