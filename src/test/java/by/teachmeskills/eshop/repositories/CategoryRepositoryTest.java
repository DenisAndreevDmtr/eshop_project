package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testGetCategoryByIdExist() {
        Optional<Category> category = categoryRepository.findById(1);
        assertThat(category).isPresent();
        assertThat(category.get().getName()).isEqualTo("Laptops");
    }

    @Test
    void testGetCategoryByIdNotExist() {
        Optional<Category> category = categoryRepository.findById(21);
        assertThat(category).isNotPresent();
    }

    @Test
    void testReadCategories() {
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories.size()).isEqualTo(20);
    }

    @Test
    void testSaveCategoryCorrect() {
        Category createdCategory = Category.builder().name("Cars").rating(4.0).build();
        Category savedCategory = categoryRepository.save(createdCategory);
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo(createdCategory.getName());
    }

    @Test
    void testSaveCategoryIncorrect() {
        Throwable thrown = catchThrowable(() -> {
            Category createdCategory = Category.builder().rating(4.0).build();
            Category savedCategory = categoryRepository.save(createdCategory);
        });
        assertThat(thrown)
                .isInstanceOf(Exception.class);
    }

    @Test
    void testDeleteCategoryCorrect() {
        categoryRepository.deleteById(20);
        assertThat(categoryRepository.getCategoryById(20)).isNull();
    }

    @Test
    void testDeleteCategoryException() {
        Throwable thrown = catchThrowable(() -> {
            categoryRepository.deleteById(21);
        });
        assertThat(thrown)
                .isInstanceOf(Exception.class);
    }
}