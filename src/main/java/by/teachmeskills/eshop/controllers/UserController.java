package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.services.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static by.teachmeskills.eshop.utils.EshopConstants.REDACTED_USER;
import static by.teachmeskills.eshop.utils.PagesPathEnum.FIELDS_REDACTION_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.SIGN_IN_PAGE;

@Log4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView openLoginPage() {
        return new ModelAndView(SIGN_IN_PAGE.getPath());
    }

    @GetMapping("/profile")
    public ModelAndView getUserData(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "5") int pageSize) {
        return userService.getDataAboutLoggedInUser(pageNumber, pageSize);
    }

    @GetMapping("/download")
    public void downloadUsersToCsvFile(HttpServletResponse response) {
        userService.downloadAllUsersToCsvFile(response);
    }

    @PostMapping("/delete")
    public ModelAndView deleteUserById(int userId) {
        return userService.deleteUserById(userId);
    }

    @GetMapping("/delete")
    public ModelAndView deleteUser(@RequestParam() int userId) {
        return userService.deleteUserAccount(userId);
    }

    @GetMapping("/redact")
    public ModelAndView getFieldsRedactionForm() {
        return new ModelAndView(FIELDS_REDACTION_PAGE.getPath());
    }

    @PostMapping("/redactfields")
    public ModelAndView redactUser(@Valid @ModelAttribute(REDACTED_USER) User user, BindingResult bindingResult) {
        return userService.redactUserFields(user, bindingResult);
    }

    @ModelAttribute(REDACTED_USER)
    public User setUpUserForm() {
        return new User();
    }
}