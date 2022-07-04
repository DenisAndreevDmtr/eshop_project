package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    Order getOrderById(int id);

    Order getOrderByUserIdAndMaxId(int id);

    List<Integer> getAllOrdersIdsByUserId(int id);

    void createOrderProduct(int productId, int orderId);
}