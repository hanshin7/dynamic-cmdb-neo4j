package top.hanshin.model.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.annotation.*;
import top.hanshin.model.node.CiModel;

@RelationshipEntity(type = "contain")
@Data
@RequiredArgsConstructor
public class CiModelRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private final CiModel startNode;

    @EndNode
    private final CiModel endNode;
}
