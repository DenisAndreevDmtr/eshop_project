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
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private static final int id = 2;
    private static final int idForDelete = 3;

    @Test
    public void testOpenCategoryPage() throws Exception {
        mockMvc.perform(get("/category/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Category page")))
                .andExpect(view().name("category"));
    }

    @Test
    public void testDeleteCategoryById() throws Exception {
        mockMvc.perform(post("/category/delete").param("categoryId", String.valueOf(idForDelete)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Data management")))
                .andExpect(view().name("datamanagement"));
    }
}