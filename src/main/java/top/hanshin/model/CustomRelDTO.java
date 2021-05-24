package top.hanshin.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.NodeEntity;
import top.hanshin.model.node.BaseNode;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CustomRelDTO {

    private String key;
    private String name;
    private String toStartName;
    private String toEndName;

}
