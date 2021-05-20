package top.hanshin.repository.relation;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import top.hanshin.model.relation.CiToBusiModelRelation;

public interface CiToBusiModelRelationRepository extends Neo4jRepository<CiToBusiModelRelation, Long> {

}
