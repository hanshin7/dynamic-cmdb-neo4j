package top.hanshin.repository.node;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import top.hanshin.model.node.CiBusiModel;

import java.util.Map;

public interface CiBusiModelRepository extends Neo4jRepository<CiBusiModel, String> {

    @Query("WITH {kvMap} as kv MATCH (n:CiBusiModel {id:{id}}) SET n += kv")
    void setOrUpdateProp(@Param("id") String id, @Param("kvMap") Map<String,Object> kvMap);

    @Query("match (n:server{id:{id}}) return properties(n)")
    Map<String, Object> getNodeMap(@Param("id") String id);

    @Query("MATCH (n:CiBusiModel{id:{id}})<-[r]-(:CI) delete r,n")
    void deleteNodeAndRelation(@Param("id") String id);

}
