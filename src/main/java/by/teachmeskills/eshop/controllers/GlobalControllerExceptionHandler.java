package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.exceptions.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.eshop.utils.EshopConstants.ERROR_PARAM;
import static by.teachmeskills.eshop.utils.PagesPathEnum.ERROR_PAGE;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthorizationException.class)
    public ModelAndView handleAuthException(Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ERROR_PARAM, ex.getMessage());
        ModelAndView model = new ModelAndView();
        model.setViewName(ERROR_PAGE.getPath());
        model.addAllObjects(modelMap);
        return model;
    }
}