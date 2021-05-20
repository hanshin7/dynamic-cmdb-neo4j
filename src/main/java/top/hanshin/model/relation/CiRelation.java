package top.hanshin.model.relation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.annotation.*;
import top.hanshin.model.node.Ci;

@RelationshipEntity(type = "CI_CI")
@Data
@RequiredArgsConstructor
public class CiRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private final Ci startNode;

    @EndNode
    private final Ci endNode;
}
