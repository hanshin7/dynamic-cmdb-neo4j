package top.hanshin.repository.relation;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import top.hanshin.model.relation.CiModelToResourceRelation;

public interface CiModelToResourceRelationRepository extends Neo4jRepository<CiModelToResourceRelation, Long> {

}
