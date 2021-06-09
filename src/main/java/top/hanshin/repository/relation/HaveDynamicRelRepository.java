package top.hanshin.repository.relation;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import top.hanshin.model.relation.HaveDynamicRel;

public interface HaveDynamicRelRepository extends Neo4jRepository<HaveDynamicRel, Long> {
}
