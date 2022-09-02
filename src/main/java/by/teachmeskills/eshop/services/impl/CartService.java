package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.model.Cart;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.UserRepository;
import by.teachmeskills.eshop.utils.PagesPathEnum;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static by.teachmeskills.eshop.utils.EshopConstants.CREATED_ORDER;
import static by.teachmeskills.eshop.utils.EshopConstants.PRODUCT;
import static by.teachmeskills.eshop.utils.PagesPathEnum.CART_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.ORDER_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.SHOPPING_CART;

@Log4j
@Service
public class CartService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CartService(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public ModelAndView addProductToCart(int productId, Cart shopCart) {
        ModelMap modelParams = new ModelMap();
        Product product = productRepository.getProductById(productId);
        shopCart.addProduct(product);
        modelParams.addAttribute(PRODUCT, product);
        modelParams.addAttribute(PagesPathEnum.SHOPPING_CART.getPath(), shopCart);
        return new ModelAndView(PRODUCT_PAGE.getPath(), modelParams);
    }

    public ModelAndView removeProductFromCart(int productId, Cart shopCart) {
        ModelMap modelParams = new ModelMap();
        Product product = productRepository.getProductById(productId);
        shopCart.removeProduct(product);
        modelParams.addAttribute(PagesPathEnum.SHOPPING_CART.getPath(), shopCart);
        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }

    public ModelAndView increaseProductQuantity(int productId, Cart shopCart) {
        ModelMap modelParams = new ModelMap();
        Product product = productRepository.getProductById(productId);
        shopCart.addProduct(product);
        modelParams.addAttribute(PRODUCT, product);
        modelParams.addAttribute(PagesPathEnum.SHOPPING_CART.getPath(), shopCart);
        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }

    public ModelAndView decreaseProductQuantity(int productId, Cart shopCart) {
        ModelMap modelParams = new ModelMap();
        Product product = productRepository.getProductById(productId);
        shopCart.decreaseQuantity(product);
        modelParams.addAttribute(PRODUCT, product);
        modelParams.addAttribute(SHOPPING_CART.getPath(), shopCart);
        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }

    public ModelAndView checkOutOrder(Cart shopCart) {
        ModelMap model = new ModelMap();
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.getUserByLogin(userLogin).ifPresent(user -> {
            Order createdOrder = orderRepository.save(createOrder(shopCart, user));
            model.addAttribute(CREATED_ORDER, createdOrder);
            shopCart.clear();
            log.info("order № " + createdOrder.getId() + "was created");
        });
        return new ModelAndView(ORDER_PAGE.getPath(), model);
    }

    private Order createOrder(Cart cart, User user) {
        Map<Product, Integer> products = cart.getProductsAndQuantity();
        Map<Product, Integer> productsForSave = Map.copyOf(products);
        BigDecimal priceOrder = cart.getTotalPrice();
        Order order = Order.builder().priceOrder(priceOrder).
                dateCreation(LocalDate.now()).
                user(user).
                products(productsForSave).
                build();
        return order;
    }
}