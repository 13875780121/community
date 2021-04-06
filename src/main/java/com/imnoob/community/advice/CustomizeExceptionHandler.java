package com.imnoob.community.advice;

import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.exception.ExceptionEnum;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ModelAndView errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e, Model model){
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {

            return null;
        } else {
            // 错误页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {

                model.addAttribute("message", ExceptionEnum.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
