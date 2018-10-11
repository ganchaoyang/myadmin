package cn.gan.framework.exception;

import cn.gan.framework.constans.ErrorCode;
import cn.gan.framework.facade.BaseResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ganchaoyang
 * @date 2018/10/11 16:38
 * @description 全局异常捕获
 */

@ControllerAdvice
public class ExceptionAdvice {

    /**
     * 全局捕获AuthorizationException异常，并进行相应处理
     */
    @ExceptionHandler({AuthorizationException.class})
    @ResponseBody
    public BaseResponse handleException(Exception e){
        return BaseResponse.error(ErrorCode.NO_PERMISSION);
    }

}
