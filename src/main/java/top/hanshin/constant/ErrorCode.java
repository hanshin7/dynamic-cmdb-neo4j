package top.hanshin.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {
	BAD_REQUEST(400, "请求参数错误。"),
	UNAUTHORIZED(401, "请求未授权或无数据权限！"),
	NOT_FOUND(404, "未找到资源。"),
	INTERNAL_SERVER_ERROR(500, "服务器内部错误。"),
	BUSINESS_INSERT_FAILED(4003, "新增失败或记录已存在！"),
	BUSINESS_UPDATE_FAILED(4004, "修改失败或记录未找到！"),
	BUSINESS_DELETE_FAILED(4005, "删除失败或不允许删除！"),
	BUSINESS_QUERY_FAILED(4006, "查询失败或记录不存在！");

	private final int code;
	
	private final String msg;
	
	ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
