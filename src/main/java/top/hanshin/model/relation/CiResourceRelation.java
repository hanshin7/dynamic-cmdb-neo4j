package top.hanshin.model.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.annotation.*;
import top.hanshin.model.node.CiModel;
import top.hanshin.model.node.CiResource;

@RelationshipEntity
@Data
@RequiredArgsConstructor
public class CiResourceRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private final CiResource startNode;

    @EndNode
    private final CiResource endNode;
}
