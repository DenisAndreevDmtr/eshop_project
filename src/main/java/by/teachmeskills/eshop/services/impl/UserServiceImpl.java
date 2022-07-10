package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.UserRepository;
import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


import static by.teachmeskills.eshop.utils.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.PROFILE_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.REGISTER_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.SIGN_IN_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.STATUS_FAIL_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.STATUS_REGISTER_PAGE;
import static by.teachmeskills.eshop.utils.RequestParamsEnum.CATEGORIES;
import static by.teachmeskills.eshop.utils.RequestParamsEnum.LOGGED_IN_USER;
import static by.teachmeskills.eshop.utils.RequestParamsEnum.LOGIN;
import static by.teachmeskills.eshop.utils.RequestParamsEnum.NUMBER_OF_PAGES;
import static by.teachmeskills.eshop.utils.RequestParamsEnum.USER_ORDERS;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public UserServiceImpl(UserRepository userRepository, CategoryService categoryService, OrderRepository orderRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public User create(User entity) {
        return userRepository.create(entity);
    }

    @Override
    public List<User> read() {
        return userRepository.read();
    }

    @Override
    public User update(User entity) {
        return userRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public ModelAndView authenticate(User user) {
        ModelAndView modelAndView = new ModelAndView();
        if (Optional.ofNullable(user).isPresent()
                && Optional.ofNullable(user.getLogin()).isPresent()
                && Optional.ofNullable(user.getPassword()).isPresent()) {
            if (userRepository.getUserByLoginAndPassword(user.getLogin(), user.getPassword()).isPresent()) {
                User loggedInUser = userRepository.getUserByLoginAndPassword(user.getLogin(), user.getPassword()).get();
                user.setId(loggedInUser.getId());
                ModelMap modelMap = new ModelMap();
                List<Category> categoriesList = categoryService.read();
                modelMap.addAttribute(CATEGORIES.getValue(), categoriesList);
                modelAndView.setViewName(HOME_PAGE.getPath());
                modelAndView.addAllObjects(modelMap);
            } else {
                modelAndView.setViewName(SIGN_IN_PAGE.getPath());
            }
        }
        return modelAndView;
    }

    @Override
    public ModelAndView registerNewUser(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        if (bindingResult.hasErrors()) {
            populateError("login", modelAndView, bindingResult);
            populateError("password", modelAndView, bindingResult);
            modelAndView.setViewName(REGISTER_PAGE.getPath());
            return modelAndView;
        }
        if (!user.checkFieldsNotNull(user)) {
            modelAndView.setViewName(REGISTER_PAGE.getPath());
            return modelAndView;
        }
        String username = user.getLogin();
        modelMap.addAttribute(LOGIN.getValue(), username);
        modelAndView.addAllObjects(modelMap);
        if (userRepository.getUserByLogin(user.getLogin()).isEmpty()) {
            User registeredUser = userRepository.create(user);
            modelAndView.setViewName(STATUS_REGISTER_PAGE.getPath());
        } else {
            modelAndView.setViewName(STATUS_FAIL_PAGE.getPath());
        }
        return modelAndView;
    }

    private void populateError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + "Error", Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }

    @Override
    public ModelAndView getDataAboutLoggedInUserPaging(User user, int number) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        User loggedInUser = userRepository.getUserByLoginAndPassword(user.getLogin(), user.getPassword()).get();
        modelMap.addAttribute(LOGGED_IN_USER.getValue(), loggedInUser);
        int idUser = loggedInUser.getId();
        user.setId(idUser);
        List<Order> userOrders=orderRepository.getAllOrdersByUserId(idUser, number);
        long numberPages = orderRepository.countAllOrdersByUser(idUser);
        List<Long> listPages = new ArrayList<>();
        for (long i = 1; i <= numberPages; i++) {
            listPages.add(i);
        }
        modelMap.addAttribute(NUMBER_OF_PAGES.getValue(), listPages);
        modelMap.addAttribute(USER_ORDERS.getValue(), userOrders);
        modelAndView.setViewName(PROFILE_PAGE.getPath());
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }
}