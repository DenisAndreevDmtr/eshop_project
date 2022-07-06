package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.model.CompositeOrderForView;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    ModelAndView authenticate(User user);

    ModelAndView getDataAboutLoggedInUser(User user);

    ModelAndView registerNewUser(User user, BindingResult bindingResult);

    ModelAndView getDataAboutLoggedInUserPaging(User user, int number);
}
