package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static by.teachmeskills.eshop.utils.EshopConstants.DISCOUNT;
import static by.teachmeskills.eshop.utils.EshopConstants.ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindAllByCategoryIdExist() {
        Pageable paging = PageRequest.of(0, 5, Sort.by(ID).ascending());
        Page pageProduct = productRepository.findAllByCategoryId(1, paging);
        assertThat(pageProduct.getTotalPages()).isEqualTo(3);
    }

    @Test
    void testFindAllByCategoryIdNotExist() {
        try {
            Pageable paging = PageRequest.of(0, 5, Sort.by(ID).ascending());
            Page pageProduct = productRepository.findAllByCategoryId(21, paging);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(EntityNotFoundException.class);
        }
    }

    @Test
    void testGetProductByIdExist() {
        Product product = productRepository.getProductById(3);
        assertThat(product.getName()).isEqualTo("Haier U1520SD");
    }

    @Test
    void testGetProductByIdNotExist() {
        try {
            Product product = productRepository.getProductById(80);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(EntityNotFoundException.class);
        }
    }

    @Test
    void testFindProductsByDiscountGreaterThan() {
        Pageable paging = PageRequest.of(0, 2, Sort.by(DISCOUNT).descending());
        Page pageProduct = productRepository.findProductsByDiscountGreaterThan(BigDecimal.ONE, paging);
        assertThat(pageProduct.getTotalPages()).isEqualTo(14);
    }
}