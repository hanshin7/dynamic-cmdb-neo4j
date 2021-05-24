package top.hanshin.repository.node;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import top.hanshin.model.node.CiModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CiModelRepository extends Neo4jRepository<CiModel, String> {
    @Query("MATCH (node:CiModel) RETURN node")
    List<CiModel> tree();

    Optional<CiModel> findByCode(String code);

    @Query("WITH {kvMap} as kv MATCH (n:CiModel{id:{id}}) SET n += kv")
    void setOrUpdateProp(@Param("id") String id, @Param("kvMap") Map<String,Object> kvMap);
}
