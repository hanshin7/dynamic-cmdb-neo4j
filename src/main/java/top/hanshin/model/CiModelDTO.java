package top.hanshin.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.NodeEntity;
import top.hanshin.model.node.BaseNode;

import java.util.Map;

@Data
public class CiModelDTO {

    private String code;
    private String parentCode;
    private String name;

    //叶子节点为true
    private Boolean endFlag;
    //业务表单字段属性
    private Map<String,Object> props;

}
