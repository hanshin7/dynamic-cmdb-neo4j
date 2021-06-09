package top.hanshin.repository.relation;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import top.hanshin.model.node.CiModel;
import top.hanshin.model.relation.CiModelRelation;

import java.util.Optional;

public interface CiModelRelationRepository extends Neo4jRepository<CiModelRelation, Long> {

    Long deleteByEndNode(CiModel endNode);

}
