package top.hanshin.model.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.annotation.*;
import top.hanshin.model.node.Ci;
import top.hanshin.model.node.CiBusiModel;

@RelationshipEntity(type = "CI_MODEL")
@Data
@RequiredArgsConstructor
public class CiToBusiModelRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private final Ci startNode;

    @EndNode
    private final CiBusiModel endNode;
}
