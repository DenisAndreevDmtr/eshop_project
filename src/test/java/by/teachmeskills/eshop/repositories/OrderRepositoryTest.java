package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Order;
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

import static by.teachmeskills.eshop.utils.EshopConstants.ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testGetOrderByIdExist() {
        Order foundOrder = orderRepository.getOrderById(1);
        assertThat(foundOrder.getPriceOrder()).isEqualTo(new BigDecimal("3000.00"));
    }

    @Test
    void testGetOrderByIdNotExist() {
        try {
            Order order = orderRepository.getOrderById(5);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(EntityNotFoundException.class);
        }
    }

    @Test
    void testGetOrdersByUserIdExist() {
        Pageable paging = PageRequest.of(0, 1, Sort.by(ID).descending());
        Page<Order> orderPage = orderRepository.getOrdersByUserId(1, paging);
        assertThat(orderPage.getTotalPages()).isEqualTo(2);
    }

    @Test
    void testGetOrdersByUserIdNotExist() {
        try {
            Pageable paging = PageRequest.of(0, 1, Sort.by(ID).descending());
            Page<Order> orderPage = orderRepository.getOrdersByUserId(3, paging);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(EntityNotFoundException.class);
        }
    }

    @Test
    void testDeleteByIdExist() {
        orderRepository.deleteById(2);
        assertThat(orderRepository.getOrderById(2)).isNull();
    }

    @Test
    void testDeleteByIdNotExist() {
        try {
            orderRepository.deleteById(5);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(EntityNotFoundException.class);
        }
    }
}