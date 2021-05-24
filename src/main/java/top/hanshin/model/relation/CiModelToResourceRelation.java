package top.hanshin.model.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.annotation.*;
import top.hanshin.model.node.CiModel;
import top.hanshin.model.node.CiResource;

@RelationshipEntity(type = "CI_MODEL")
@Data
@RequiredArgsConstructor
public class CiModelToResourceRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private final CiModel startNode;

    @EndNode
    private final CiResource endNode;
}
