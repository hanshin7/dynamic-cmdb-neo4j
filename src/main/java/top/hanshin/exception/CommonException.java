package top.hanshin.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.hanshin.constant.ErrorCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CommonException extends RuntimeException{
    private int code;
    private String msg;

    public CommonException(ErrorCode errorCode){
        code = errorCode.getCode();
        msg = errorCode.getMsg();
    }
    public CommonException(String msg){
        this.code = ErrorCode.NOT_FOUND.getCode();
        this.msg = msg;
    }

}
