package com.easybytes.easyschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//@Slf4j
//@ControllerAdvice
//public class GlobalExceptionController {
//    @ExceptionHandler(Exception.class)
//    public ModelAndView exceptionHandler(Exception exception) {
//        ModelAndView errorPage = new ModelAndView();
//        errorPage.setViewName("error");
//        errorPage.addObject("errormsg", exception.getMessage());
//        return errorPage;
//    }
//}

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionController {

    /*
    @ExceptionHandler will register the given method for a given
    exception type, so that ControllerAdvice can invoke this method
    logic if a given exception type is thrown inside the web application.
    * */
    @ExceptionHandler({Exception.class})
    public ModelAndView exceptionHandler(Exception exception){
        String errorMsg = null;
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        if(exception.getMessage()!=null){
            errorMsg = exception.getMessage();
        }else if (exception.getCause()!=null){
            errorMsg = exception.getCause().toString();
        }else if (exception!=null){
            errorMsg = exception.toString();
        }
        errorPage.addObject("errormsg", errorMsg);
        return errorPage;
    }

}