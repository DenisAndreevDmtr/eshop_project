package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.entities.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public interface UserService extends BaseService<User> {

    ModelAndView registerNewUser(User user, BindingResult bindingResult);

    ModelAndView getDataAboutLoggedInUser(int pageNumber, int pageSize);

    void downloadAllUsersToCsvFile(HttpServletResponse response);

    ModelAndView deleteUserById(int id);

    ModelAndView redactUserFields(User user, BindingResult bindingResult);

    ModelAndView deleteUserAccount(int id);
}