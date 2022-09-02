package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order getOrderById(int id);

    Page<Order> getOrdersByUserId(int id, Pageable pageable);

    @Modifying
    @Query("delete from Order where id = :idParam")
    void deleteById(@Param("idParam") int id);
}