package top.hanshin.model.relation;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

@Data
public class BaseRel {
    @Id
    @GeneratedValue
    private Long id;
}
