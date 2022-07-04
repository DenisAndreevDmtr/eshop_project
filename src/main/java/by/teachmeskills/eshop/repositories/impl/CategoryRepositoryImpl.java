package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL_CATEGORIES = "SELECT * FROM category";
    private static final String GET_CATEGORY_NAME_BY_ID = "SELECT internet_shop.category.name FROM internet_shop.category WHERE id=?";
    private static final String CREATE_NEW_CATEGORY = "INSERT INTO category (name, rating, image_Path) VALUES (?, ?, ?)";
    private static final String GET_CATEGORY_BY_NAME = "SELECT * FROM category WHERE name=?";
    private static final String UPDATE_CATEGORY = "UPDATE category SET rating=? WHERE name=?";
    private static final String DELETE_CATEGORY = "DELETE FROM category WHERE id=?";
    private static final String GET_CATEGORY_BY_ID = "SELECT * FROM category WHERE id=?";

    public CategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> getAllCategories() {
        return jdbcTemplate.query(GET_ALL_CATEGORIES, (rs, rowNum) -> Category.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .rating(rs.getInt("rating"))
                .imageName(rs.getString("image_path"))
                .build()
        );
    }

    @Override
    public String getCategoryNameByID(int id) {
        return jdbcTemplate.queryForObject(GET_CATEGORY_NAME_BY_ID, (RowMapper<String>) (rs, rowNum) -> rs.getString("name"), id);
    }


    @Override
    public Category getCategoryById(int id) {
        return jdbcTemplate.queryForObject(GET_CATEGORY_BY_ID, (RowMapper<Category>) (rs, rowNum) -> Category.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .imageName(rs.getString("image_path"))
                .rating(rs.getInt("rating"))
                .build(), id);
    }

    //method should be updated
    @Override
    public Category create(Category entity) {
        jdbcTemplate.update(CREATE_NEW_CATEGORY, entity.getName(), entity.getRating(), entity.getImageName());
        return getCategoryByName(entity.getName());
    }


    private Category getCategoryByName(String nameCategory) {
        return jdbcTemplate.queryForObject(GET_CATEGORY_BY_NAME, (RowMapper<Category>) (rs, rowNum) -> Category.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .imageName(rs.getString("image_path"))
                .rating(rs.getInt("rating"))
                .build(), nameCategory);
    }

    //method should be updated
    @Override
    public List<Category> read() {
        return getAllCategories();
    }

    //method should be updated
    @Override
    public Category update(Category entity) {
        jdbcTemplate.update(UPDATE_CATEGORY, entity.getRating(),entity.getName());
        return getCategoryByName(entity.getName());
    }

    //method should be updated
    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_CATEGORY, id);
    }
}