package by.teachmeskills.eshop.controllers;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.eshop.utils.PagesPathEnum.ERROR_PAGE;

@Log4j
@ControllerAdvice(basePackages = "by.teachmeskills.eshop.controllers")
public class GlobalControllerException {

    @ExceptionHandler
    public ModelAndView globalException(Exception e) {
        log.error(e.getMessage());
        return new ModelAndView(ERROR_PAGE.getPath());
    }
}