package top.hanshin.repository.relation;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import top.hanshin.model.node.CiModel;
import top.hanshin.model.node.CiResource;
import top.hanshin.model.relation.CiModelRelation;
import top.hanshin.model.relation.CiResourceRelation;

import java.util.List;
import java.util.Map;

public interface CiResourceRelationRepository extends Neo4jRepository<CiResourceRelation, Long> {

    @Query("MATCH (startNode)-[r]->(:CiResource{id:{id}}) return startNode,r")
    List<Map<String, Object>> queryDownRel(String id);

    @Query("MATCH (:CiResource{id:{id}})-[r]->(endNode) return endNode,r")
    List<Map<String, Object>> queryUpRel(String id);
}
