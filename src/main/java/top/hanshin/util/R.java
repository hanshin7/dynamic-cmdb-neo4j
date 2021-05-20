package top.hanshin.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.hanshin.constant.SysConstance;
import top.hanshin.exception.CommonException;

@Data
@NoArgsConstructor
public class R<T> {

	private int code;
	
	private String msg;
	
	private Type type;
	
	private T data;
	
	private R(int code, String msg, Type type, T data) {
		this.code = code;
		this.msg = msg;
		this.type = type;
		this.data = data;
	}
	
	public static <T> R<T> data(T data) {
		return new R<>(SysConstance.HTTP_OK, SysConstance.RESPONSE_SUCCESS_MESSAGE, Type.INFO, data);
	}

    public static R<Void> fail(CommonException e) {
        return new R<>(e.getCode(), e.getMsg(), Type.WARN, null);
    }
	
	public static R<Void> fail(int code, String msg, Type type) {
		return new R<>(code, msg, type, null);
	}

    public static R<Void> fail() {
        return new R<>(SysConstance.ERRCODE_500, SysConstance.RESPONSE_FAIL_MESSAGE, Type.WARN, null);
    }

	public static R<Void> success() {
		return new R<>(SysConstance.HTTP_OK, SysConstance.RESPONSE_SUCCESS_MESSAGE, Type.INFO, null);
	}
	
	public static R<Void> status(boolean b) {
		if (b) {
			return success();
		}
		return fail();
	}
	
}
