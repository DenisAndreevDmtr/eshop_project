package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.OrderRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_NEW_ORDER = "INSERT INTO internet_shop.order (price, date_Order, user_id) VALUES (?, ?, ?)";
    private static final String GET_ORDER_BY_USER_ID = "SELECT * FROM internet_shop.order WHERE user_id=? ORDER BY id DESC LIMIT 1";
    private static final String INSERT_NEW_ORDER_PRODUCT = "INSERT INTO internet_shop.order_product (product_id, order_id) VALUES (?, ?)";
    private static final String GET_ALL_ORDERS_ID_BY_USER_ID = "SELECT * FROM internet_shop.order WHERE user_id=?";
    private static final String GET_ORDER_BY_ID_ORDER = "SELECT * FROM internet_shop.order WHERE id=?";
    private static final String GET_ALL_ORDERS = "SELECT * FROM internet_shop.order";
    private static final String UPDATE_ORDER = "UPDATE internet_shop.order SET price=? WHERE id=?";
    private static final String DELETE_ORDER = "DELETE FROM internet_shop.order WHERE id=?";

    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order create(Order entity) {
        jdbcTemplate.update(INSERT_NEW_ORDER, entity.getPriceOrder(), Date.valueOf(entity.getDate()), entity.getUserId());
        Order order=getOrderByUserIdAndMaxId(entity.getUserId());
        List<Product> productsInOrder = entity.getProductsInOrder();
        for (Product p : productsInOrder) {
            createOrderProduct(p.getId(), order.getId());
        }
        return getOrderByUserIdAndMaxId(entity.getUserId());
    }

    public void createOrderProduct(int idProduct, int idOrder) {
        jdbcTemplate.update(INSERT_NEW_ORDER_PRODUCT, idProduct, idOrder);
    }


    @Override
    public Order getOrderByUserIdAndMaxId(int id) {
        return jdbcTemplate.queryForObject(GET_ORDER_BY_USER_ID, (RowMapper<Order>) (rs, rowNum) ->
                Order.builder()
                        .id(rs.getInt("id"))
                        .priceOrder(rs.getBigDecimal("price"))
                        .date(rs.getDate("date_Order").toLocalDate())
                        .userId(rs.getInt("user_id"))
                        .build(), id);
    }

    @Override
    public List<Integer> getAllOrdersIdsByUserId(int id) {
        return jdbcTemplate.query(GET_ALL_ORDERS_ID_BY_USER_ID, (rs, rowNum) ->
                rs.getInt("id"), id);
    }

    @Override
    public Order getOrderById(int id) {
        return jdbcTemplate.queryForObject(GET_ORDER_BY_ID_ORDER, (RowMapper<Order>) (rs, rowNum) ->
                Order.builder()
                        .id(rs.getInt("id"))
                        .priceOrder(rs.getBigDecimal("price"))
                        .date(rs.getDate("date_Order").toLocalDate())
                        .userId(rs.getInt("user_id"))
                        .build(), id);
    }

    @Override
    public List<Order> read() {
        return jdbcTemplate.query(GET_ALL_ORDERS, (rs, rowNum) -> Order.builder()
                .id(rs.getInt("id"))
                .priceOrder(rs.getBigDecimal("price"))
                .date(rs.getDate("date_Order").toLocalDate())
                .userId(rs.getInt("user_id"))
                .build()
        );
    }

    //method should be updated
    @Override
    public Order update(Order entity) {
        jdbcTemplate.update(UPDATE_ORDER, entity.getPriceOrder(), entity.getId());
        return getOrderById(entity.getId());
    }

    //method should be updated
    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_ORDER, id);
    }
}