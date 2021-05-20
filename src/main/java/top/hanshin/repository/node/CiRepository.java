package top.hanshin.repository.node;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import top.hanshin.model.node.Ci;

import java.util.List;
import java.util.Optional;

public interface CiRepository extends Neo4jRepository<Ci, String> {
    @Query("MATCH (node:CI) RETURN node")
    List<Ci> tree();

    Optional<Ci> findByCode(String code);
}
