package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.ProductRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL_PRODUCTS = "SELECT * FROM product";
    private static final String GET_ALL_PRODUCTS_BY_CATEGORY_ID = "SELECT * FROM product WHERE category_id=?";
    private static final String GET_PRODUCT_BY_ID_PRODUCT = "SELECT * FROM product WHERE id=?";
    private static final String GET_ALL_PRODUCTS_BY_REQUEST = " SELECT * FROM product WHERE name LIKE ? or description LIKE ?";
    private static final String GET_ALL_PRODUCTS_BY_ID_ORDER = "SELECT internet_shop.product.*\n" +
            "FROM internet_shop.product\n" +
            "INNER JOIN internet_shop.order_product\n" +
            "ON order_product.product_id = internet_shop.product.id\n" +
            "INNER JOIN internet_shop.order\n" +
            "ON internet_shop.order.id = internet_shop.order_product.order_id\n" +
            "WHERE internet_shop.order_product.order_id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE product SET name=?, description=?, price=?, category_id=?, image_Path=? WHERE id=?";
    private static final String DELETE_PRODUCT = "DELETE FROM product WHERE id=?";
    private static final String INSERT_NEW_PRODUCT = "INSERT INTO product (name, description, price, category_id, image_Path) VALUES (?, ?, ?, ?, ?)";

    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAllProductsByCategoryId(int id) {
        return jdbcTemplate.query(GET_ALL_PRODUCTS_BY_CATEGORY_ID, (rs, rowNum) -> Product.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .imageName(rs.getString("image_Path"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .categoryId(rs.getInt("category_id"))
                .build(), id);
    }

    public Product getProductById(int id) {
        return jdbcTemplate.queryForObject(GET_PRODUCT_BY_ID_PRODUCT, (RowMapper<Product>) (rs, rowNum) -> Product.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .imageName(rs.getString("image_Path"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .categoryId(rs.getInt("category_id"))
                .build(), id);
    }

    @Override
    public List<Product> getListProductsByNameOrDesc(String param) {
        List<Product> productList = null;
        String requestDB = '%' + param + '%';
        try {
            productList = jdbcTemplate.query(GET_ALL_PRODUCTS_BY_REQUEST, (rs, rowNum) -> Product.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .imageName(rs.getString("image_Path"))
                    .description(rs.getString("description"))
                    .price(rs.getBigDecimal("price"))
                    .categoryId(rs.getInt("category_id"))
                    .build(), requestDB, requestDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    //method should be updated
    @Override
    public Product create(Product entity) {
        jdbcTemplate.update(INSERT_NEW_PRODUCT, entity.getName(), entity.getDescription(), entity.getPrice(), entity.getCategoryId(),
                entity.getImageName());
        return entity;
    }

    @Override
    public List<Product> read() {
        return jdbcTemplate.query(GET_ALL_PRODUCTS, (rs, rowNum) -> Product.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .imageName(rs.getString("image_Path"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .categoryId(rs.getInt("category_id"))
                .build()
        );
    }

    @Override
    public List<Product> getAllProductsByOrderId(int id) {
        List<Product> productList = null;
        try {
            productList = jdbcTemplate.query(GET_ALL_PRODUCTS_BY_ID_ORDER, (rs, rowNum) -> Product.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .imageName(rs.getString("image_Path"))
                    .description(rs.getString("description"))
                    .price(rs.getBigDecimal("price"))
                    .categoryId(rs.getInt("category_id"))
                    .build(), id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    //method should be updated
    @Override
    public Product update(Product entity) {
        jdbcTemplate.update(UPDATE_PRODUCT, entity.getName(), entity.getDescription(), entity.getPrice(), entity.getCategoryId(), entity.getImageName(), entity.getId());
        return entity;
    }

    //method should be updated
    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_PRODUCT, id);
    }
}