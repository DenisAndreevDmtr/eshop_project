package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.services.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static by.teachmeskills.eshop.utils.EshopConstants.USER;
import static by.teachmeskills.eshop.utils.PagesPathEnum.REGISTER_PAGE;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView openRegisterPage() {
        return new ModelAndView(REGISTER_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView registerUser(@Valid @ModelAttribute(USER) User user, BindingResult bindingResult) {
        return userService.registerNewUser(user, bindingResult);
    }

    @ModelAttribute(USER)
    public User setUpUserForm() {
        return new User();
    }
}