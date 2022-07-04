package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.entities.Product;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    List<Product> getAllProductsByCategory(int categoryId);

    ModelAndView getProductData(int id);

    ModelAndView getSearchResult(String searchParametr);
}
