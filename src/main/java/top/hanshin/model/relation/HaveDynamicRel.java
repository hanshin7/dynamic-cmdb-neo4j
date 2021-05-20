package top.hanshin.model.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.neo4j.ogm.annotation.typeconversion.DateString;
import top.hanshin.model.node.BaseNode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@RelationshipEntity(type = "contain")
public class HaveDynamicRel<S extends BaseNode, E extends BaseNode> extends BaseRel {

    @StartNode
    private S startNode;

    @EndNode
    private E endNode;

    @DateString(value = "yyyy-MM-dd HH:mm:ss.SSS", zoneId = "Asia/Shanghai")
    private Date createTime;
}
