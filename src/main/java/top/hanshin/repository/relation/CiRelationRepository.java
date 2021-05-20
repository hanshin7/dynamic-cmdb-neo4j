package top.hanshin.repository.relation;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import top.hanshin.model.node.Ci;
import top.hanshin.model.relation.CiRelation;

public interface CiRelationRepository extends Neo4jRepository<CiRelation, Long> {

    Long deleteByEndNode(Ci endNode);
}
