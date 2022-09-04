package by.teachmeskills.eshop.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private static final int id = 4;
    private static final int idForDelete = 5;

    @Test
    public void openProductPage() throws Exception {
        mockMvc.perform(get("/product/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Asus VivoBook S14 S433EA-AM464")))
                .andExpect(view().name("product"));
    }

    @Test
    public void testDeleteProductById() throws Exception {
        mockMvc.perform(post("/product/delete").param("productId", String.valueOf(idForDelete)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Data management")))
                .andExpect(view().name("datamanagement"));
    }

    @Test
    public void testOpenHotPricesProducts() throws Exception {
        mockMvc.perform(get("/product/hotprices"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hot prices page")))
                .andExpect(view().name("hotprices"));
    }
}