package top.hanshin.model;

import lombok.Data;

import java.util.List;

@Data
public class CiModelVO {

	private static final long serialVersionUID = -1508889323949003666L;
	
	private String id;

    private String code;

    private String parentCode;

    private String name;

    private boolean isEndItem;

    private List<CiModelVO> children;

}
