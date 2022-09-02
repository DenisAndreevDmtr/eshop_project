package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Role;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.repositories.RoleRepository;
import by.teachmeskills.eshop.repositories.UserRepository;
import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.UserService;
import by.teachmeskills.eshop.utils.CsvUtil;

import by.teachmeskills.eshop.utils.PagingUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static by.teachmeskills.eshop.utils.EshopConstants.CATEGORIES_CSV_FILE_NAME;
import static by.teachmeskills.eshop.utils.EshopConstants.ERROR_PARAM;
import static by.teachmeskills.eshop.utils.EshopConstants.FIELD_LOGIN;
import static by.teachmeskills.eshop.utils.EshopConstants.FIELD_PASSWORD;
import static by.teachmeskills.eshop.utils.EshopConstants.ID;
import static by.teachmeskills.eshop.utils.EshopConstants.LOGGED_IN_USER;
import static by.teachmeskills.eshop.utils.EshopConstants.OPERATION_STATUS_FAIL;
import static by.teachmeskills.eshop.utils.EshopConstants.OPERATION_STATUS_SUCCESS;
import static by.teachmeskills.eshop.utils.EshopConstants.ROLE_USER;
import static by.teachmeskills.eshop.utils.EshopConstants.USERS_CSV_FILE_NAME;
import static by.teachmeskills.eshop.utils.EshopConstants.USER_ORDERS;
import static by.teachmeskills.eshop.utils.PagesPathEnum.DATA_MANAGEMENT_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.FIELDS_REDACTION_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.PROFILE_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.REGISTER_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.SIGN_IN_PAGE;

@Log4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final OrderRepository orderRepository;
    private final CsvUtil csvUtil;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PagingUtil pagingUtil;

    public UserServiceImpl(UserRepository userRepository, CategoryService categoryService, OrderRepository orderRepository, CsvUtil csvUtil, PasswordEncoder passwordEncoder, RoleRepository roleRepository, PagingUtil pagingUtil) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.orderRepository = orderRepository;
        this.csvUtil = csvUtil;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.pagingUtil = pagingUtil;
    }

    @Override
    public User create(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        Optional<Role> role = roleRepository.findRoleByName(ROLE_USER);
        if (role.isPresent()) {
            entity.setRole(role.get());
            entity.setBalance(BigDecimal.ZERO);
        }
        return userRepository.save(entity);
    }

    @Override
    public List<User> read() {
        return userRepository.findAll();
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Override
    public ModelAndView deleteUserAccount(int id) {
        ModelMap model = new ModelMap();
        try {
            delete(id);
            log.info("User with id " + id + "was deleted");
            model.addAttribute(OPERATION_STATUS_SUCCESS, "You have deleted your account!");
            return new ModelAndView(SIGN_IN_PAGE.getPath(), model);
        } catch (Exception e) {
            log.error("Сan`t delete user with id id");
            model.addAttribute(OPERATION_STATUS_FAIL, "Сan`t delete your account. Try latter!");
            return new ModelAndView(PROFILE_PAGE.getPath(), model);
        }
    }

    @Override
    public ModelAndView deleteUserById(int id) {
        ModelMap model = new ModelMap();
        if (id <= 1) {
            log.error("Сan`t delete user with id " + id);
            model.addAttribute(OPERATION_STATUS_FAIL, "Сan`t delete admin!");
            return new ModelAndView(DATA_MANAGEMENT_PAGE.getPath(), model);
        }
        try {
            userRepository.deleteById(id);
            model.addAttribute(OPERATION_STATUS_SUCCESS, "You deleted user with id № " + id);
            log.info("User with id " + id + "was deleted");
        } catch (Exception e) {
            log.error("Сan`t delete user with id " + id);
            model.addAttribute(OPERATION_STATUS_FAIL, "Сan`t delete user with id " + id + ". Check, if such id is exist");
        }
        return new ModelAndView(DATA_MANAGEMENT_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView registerNewUser(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        if (bindingResult.hasErrors() || !user.checkFieldsNotNull(user)) {
            populateError(FIELD_LOGIN, modelAndView, bindingResult);
            populateError(FIELD_PASSWORD, modelAndView, bindingResult);
            modelAndView.setViewName(REGISTER_PAGE.getPath());
            return modelAndView;
        }
        if (userRepository.getUserByLogin(user.getLogin()).isEmpty()) {
            User registeredUser = create(user);
            log.info("User with login" + registeredUser.getLogin() + " has just registered");
            modelMap.addAttribute(OPERATION_STATUS_SUCCESS, "Account with login " + registeredUser.getLogin() + ", was created!");
            return new ModelAndView(SIGN_IN_PAGE.getPath(), modelMap);
        } else {
            modelMap.addAttribute(ERROR_PARAM, "Cant create account with login " + user.getLogin() + ", cause user with such login is allready exist!");
            log.error("User with login" + user.getLogin() + " has already exist, can`t create account");
        }
        return new ModelAndView(REGISTER_PAGE.getPath(), modelMap);
    }

    private void populateError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + "Error", Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }

    @Override
    public ModelAndView getDataAboutLoggedInUser(int pageNumber, int pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.getUserByLogin(userLogin).ifPresent(user -> {
            modelMap.addAttribute(LOGGED_IN_USER, user);
            Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(ID).descending());
            Page<Order> userOrders = orderRepository.getOrdersByUserId(user.getId(), paging);
            modelMap.addAttribute(USER_ORDERS, userOrders.getContent());
            pagingUtil.setPagingAttributes(modelMap, userOrders, pageNumber, pageSize);
            modelAndView.setViewName(PROFILE_PAGE.getPath());
            modelAndView.addAllObjects(modelMap);
        });

        return modelAndView;
    }

    @Override
    public void downloadAllUsersToCsvFile(HttpServletResponse response) {
        try {
            Writer writer = csvUtil.setResponsePropertiesAndGetWriter(response, USERS_CSV_FILE_NAME);
            List<User> users = read();
            csvUtil.saveAllUsersToCsvFile(writer, users);
            log.info("Users have been downloaded to file name " + USERS_CSV_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("error in method saveAllUsersToCsvFile " + e.getMessage());
        }
    }

    @Override
    public ModelAndView redactUserFields(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        if (bindingResult.hasErrors()) {
            populateError(FIELD_LOGIN, modelAndView, bindingResult);
            populateError(FIELD_PASSWORD, modelAndView, bindingResult);
            modelAndView.setViewName(FIELDS_REDACTION_PAGE.getPath());
            return modelAndView;
        }
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userRepository.getUserByLogin(user.getLogin()).isEmpty() || userLogin.equals(user.getLogin())) {
            userRepository.getUserByLogin(userLogin).ifPresent(userInSystem -> {
                userInSystem.setPassword(passwordEncoder.encode(user.getPassword()));
                userInSystem.setLogin(user.getLogin());
                userInSystem.setName(user.getName());
                userInSystem.setSurname(user.getSurname());
                userInSystem.setEMail(user.getEMail());
                update(userInSystem);
                SecurityContextHolder.getContext().setAuthentication(null);
                log.info("User with id " + userInSystem.getId() + " has just changed his user fields with information");
                modelMap.addAttribute(OPERATION_STATUS_SUCCESS, "You have changed your info! Please relogin!");
                modelAndView.setViewName(SIGN_IN_PAGE.getPath());
            });

        } else {
            modelAndView.setViewName(FIELDS_REDACTION_PAGE.getPath());
            modelMap.addAttribute(OPERATION_STATUS_FAIL, "Cant change now. Try latter!");
        }
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }
}