package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.entities.Category;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public interface CategoryService extends BaseService<Category> {
    ModelAndView getCategoryData(int id, int pageNumber, int pageSize);

    ModelAndView getHomePageData(int pageNumber, int pageSize);

    ModelAndView getSearchPageData();

    ModelAndView saveCategoriesFromFile(MultipartFile file);

    void downloadCategoriesToCsvFile(HttpServletResponse response);

    void delete(int categoryId);

    ModelAndView deleteCategoryById(int categoryId);
}