package top.hanshin.model.node;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.NodeEntity;


@NodeEntity("CI")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Ci extends BaseNode {

    //节点基础属性
    //@Id
    private String code;
    private String parentCode;
    private String name;

    //叶子节点为true
    private Boolean endFlag;
    //业务表单字段属性
    //@Convert
    private String props; //map

}
