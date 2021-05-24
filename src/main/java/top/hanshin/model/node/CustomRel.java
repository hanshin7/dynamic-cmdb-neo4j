package top.hanshin.model.node;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity("CustomRel")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CustomRel extends BaseNode {

    private String key;
    private String name;
    //目标 -》源
    private String toStartName;
    //源 -》目标
    private String toEndName;

}
