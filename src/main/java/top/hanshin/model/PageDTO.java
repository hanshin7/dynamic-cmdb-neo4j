package top.hanshin.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageDTO implements Serializable {
	
	private static final long serialVersionUID = 2228009995751657718L;

    @ApiModelProperty(required = true)
	private Integer current;

    @ApiModelProperty(required = true)
	private Integer size;

    @ApiModelProperty(required = true)
	private String ciKey;

}
