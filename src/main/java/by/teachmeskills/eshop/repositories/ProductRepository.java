package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Product;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {

    List<Product> getAllProductsByCategoryId(int id);

    Product getProductById(int id);

    List<Product> getListProductsByNameOrDesc(String param);

    List<Product> getAllProductsByOrderId(int id);
}