package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public interface ProductService extends BaseService<Product> {
    Page<Product> getAllProductsByCategory(int categoryId, int pageNumber, int pageSize);

    ModelAndView getProductData(int id);

    ModelAndView saveProductsFromFile(MultipartFile file);

    void downloadProductsToCsvFile(HttpServletResponse response);

    ModelAndView searchProducts(SearchParamsDto searchParamsDto, int pageNumber, int pageSize);

    ModelAndView deleteProductById(int id);

    ModelAndView getHotPricesProducts(int pageNumber, int pageSize);
}