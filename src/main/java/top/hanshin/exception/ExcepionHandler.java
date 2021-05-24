package top.hanshin.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.hanshin.util.R;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExcepionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R<Void> errorHandler(HttpServletRequest request, Exception e) {
        if(e instanceof CommonException) {
            return R.fail((CommonException) e);
        }
        if (e instanceof NoHandlerFoundException) {
            Exception excp = new CommonException(request == null ? "" : request.getRequestURI());
            return R.fail((CommonException)excp);
        }
        return R.fail();
    }
}
