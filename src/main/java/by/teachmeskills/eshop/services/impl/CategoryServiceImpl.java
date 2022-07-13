package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.eshop.utils.PagesPathEnum.CATEGORY_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.SEARCH_PRODUCT_PAGE;
import static by.teachmeskills.eshop.utils.RequestParamsEnum.CATEGORIES;
import static by.teachmeskills.eshop.utils.RequestParamsEnum.CATEGORY;
import static by.teachmeskills.eshop.utils.RequestParamsEnum.NUMBER_OF_PAGES;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    @Override
    public Category create(Category entity) {
        Category category = categoryRepository.create(entity);
        return category;
    }

    @Override
    public List<Category> read() {
        return categoryRepository.read();
    }

    @Override
    public Category update(Category entity) {
        Category category = categoryRepository.update(entity);
        return category;
    }

    @Override
    public void delete(int id) {
        categoryRepository.delete(id);
    }

    @Override
    public ModelAndView getCategoryData(int id) {
        ModelMap model = new ModelMap();

        Category category = categoryRepository.getCategoryById(id);
        if (Optional.ofNullable(category).isPresent()) {
            List<Product> products = productService.getAllProductsByCategory(category.getId());
            category.setProductList(products);
            long numberPages = productService.countAllProductsByCategory(id);
            List<Long> listPages = new ArrayList<>();
            for (long i = 1; i <= numberPages; i++) {
                listPages.add(i);
            }
            model.addAttribute(NUMBER_OF_PAGES.getValue(), listPages);
            model.addAttribute(CATEGORY.getValue(), category);
        }
        return new ModelAndView(CATEGORY_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getHomePageData() {
        ModelMap model = new ModelMap();
        List<Category> categoriesList = categoryRepository.read();
        model.addAttribute(CATEGORIES.getValue(), categoriesList);
        return new ModelAndView(HOME_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getSearchPageData() {
        ModelMap model = new ModelMap();
        List<Category> categoriesList = read();
        model.addAttribute(CATEGORIES.getValue(), categoriesList);
        return new ModelAndView(SEARCH_PRODUCT_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getCategoryDataPaging(int id, int number) {
        ModelMap model = new ModelMap();
        Category category = categoryRepository.getCategoryById(id);
        if (Optional.ofNullable(category).isPresent()) {
            List<Product> products = productService.getAllProductsByCategoryPaging(category.getId(), number);
            category.setProductList(products);
            long numberPages = productService.countAllProductsByCategory(id);
            List<Long> listPages = new ArrayList<>();
            for (long i = 1; i <= numberPages; i++) {
                listPages.add(i);
            }
            model.addAttribute(NUMBER_OF_PAGES.getValue(), listPages);
            model.addAttribute(CATEGORY.getValue(), category);
        }
        return new ModelAndView(CATEGORY_PAGE.getPath(), model);
    }
}