package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.model.Cart;
import by.teachmeskills.eshop.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static by.teachmeskills.eshop.utils.EshopConstants.SHOPPING_CART;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private static final Cart cart = new Cart();
    private static final int id = 2;

    @Test
    public void testOpenCartPage() throws Exception {
        mockMvc.perform(get("/cart"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Shopping cart")))
                .andExpect(view().name("cart"));
    }

    @Test
    public void testCartAdd() throws Exception {
        mockMvc.perform(get("/cart/add").param("product_id", String.valueOf(id)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Lenovo IdeaPad 3 15IGL05 81WQ0023RE")))
                .andExpect(view().name("product"));
    }

    @Test
    public void testCartRemove() throws Exception {
        cart.addProduct(productRepository.getProductById(id));
        mockMvc.perform(get("/cart/remove").param("product_id", String.valueOf(id)).flashAttr(SHOPPING_CART, cart))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Shopping cart")))
                .andExpect(view().name("cart"));
    }

    @Test
    public void testCartIncrease() throws Exception {
        cart.addProduct(productRepository.getProductById(id));
        mockMvc.perform(get("/cart/increase").param("product_id", String.valueOf(id)).flashAttr(SHOPPING_CART, cart))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Shopping cart")))
                .andExpect(view().name("cart"));
    }

    @Test
    public void testCartDecrease() throws Exception {
        cart.addProduct(productRepository.getProductById(id));
        mockMvc.perform(get("/cart/decrease").param("product_id", String.valueOf(id)).flashAttr(SHOPPING_CART, cart))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Shopping cart")))
                .andExpect(view().name("cart"));
    }

    @Test
    public void testPurchaseWithoutAuthentication() throws Exception {
        cart.addProduct(productRepository.getProductById(id));
        mockMvc.perform(get("/cart/purchase").flashAttr(SHOPPING_CART, cart))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/user"));
    }
}