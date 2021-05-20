package top.hanshin.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.typeconversion.DateString;
import org.neo4j.ogm.id.UuidStrategy;

import java.util.Date;

@Data
public abstract class BaseNode {

    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    private String id;
    private String createUser;
    private String updateUser;
    @DateString(value = "yyyy-MM-dd HH:mm:ss.SSS", zoneId = "Asia/Shanghai")
    private Date createTime;
    @DateString(value = "yyyy-MM-dd HH:mm:ss.SSS", zoneId = "Asia/Shanghai")
    private Date updateTime;
}
