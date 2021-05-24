package top.hanshin.model;

import lombok.Data;

import java.util.Map;

@Data
public class CiModelUpdateDTO {

    private String id;
    private String name;

    //叶子节点为true
    private Boolean endFlag;
    //业务表单字段属性
    private Map<String,Object> props;

}
