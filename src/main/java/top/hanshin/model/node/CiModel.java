package top.hanshin.model.node;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.springframework.context.annotation.Primary;

import java.util.Map;

@NodeEntity("CiModel")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CiModel extends BaseNode {

    //节点基础属性
    //@Id
    private String code;
    private String parentCode;
    private String name;

    //叶子节点为true
    private Boolean endFlag;
    //业务表单字段属性
    //@Convert(graphPropertyType = Map.class)
    private String props; //map

}
