package top.hanshin.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CustomRelUpdateDTO {

    private String id;
    //private String key;
    private String name;
    private String toStartName;
    private String toEndName;

}
