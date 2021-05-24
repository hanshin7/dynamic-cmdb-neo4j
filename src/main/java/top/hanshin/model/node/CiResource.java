package top.hanshin.model.node;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.Labels;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CiResource extends BaseNode {

    private String ciKey;

    @Labels
    private Set<String> labels = new HashSet<>();

    //不同资源扩展不同字段属性
//    @Properties(prefix = "form")
//    private Map<String, Object> properties = new HashMap<>();
}
