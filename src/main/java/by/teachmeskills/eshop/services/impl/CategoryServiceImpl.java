package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.ProductService;
import by.teachmeskills.eshop.utils.CsvUtil;

import by.teachmeskills.eshop.utils.PagingUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.eshop.utils.EshopConstants.CATEGORIES;
import static by.teachmeskills.eshop.utils.EshopConstants.CATEGORIES_CSV_FILE_NAME;
import static by.teachmeskills.eshop.utils.EshopConstants.CATEGORY;
import static by.teachmeskills.eshop.utils.EshopConstants.ID;
import static by.teachmeskills.eshop.utils.EshopConstants.OPERATION_STATUS_FAIL;
import static by.teachmeskills.eshop.utils.EshopConstants.OPERATION_STATUS_SUCCESS;
import static by.teachmeskills.eshop.utils.PagesPathEnum.CATEGORY_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.DATA_MANAGEMENT_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.SEARCH_PRODUCT_PAGE;

@Log4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final CsvUtil csvUtil;
    private final PagingUtil pagingUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductService productService, CsvUtil csvUtil, PagingUtil pagingUtil) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
        this.csvUtil = csvUtil;
        this.pagingUtil = pagingUtil;
    }

    @Override
    public Category create(Category entity) {
        Category category = categoryRepository.save(entity);
        return category;
    }

    @Override
    public List<Category> read() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Category entity) {
        Category category = categoryRepository.save(entity);
        return category;
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public ModelAndView deleteCategoryById(int id) {
        ModelMap model = new ModelMap();
        try {
            categoryRepository.deleteById(id);
            model.addAttribute(OPERATION_STATUS_SUCCESS, "You deleted category with id № " + id);
            log.info("Сategory with id " + id + " has been deleted");
        } catch (Exception e) {
            log.error("Сan`t delete category with id " + id);
            model.addAttribute(OPERATION_STATUS_FAIL, "Сan`t delete category with id " + id + ". Check, if such id is exist");
        }
        return new ModelAndView(DATA_MANAGEMENT_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getCategoryData(int id, int pageNumber, int pageSize) {
        ModelMap model = new ModelMap();
        Category category = categoryRepository.getCategoryById(id);
        if (Optional.ofNullable(category).isPresent()) {
            Page<Product> products = productService.getAllProductsByCategory(category.getId(), pageNumber, pageSize);
            category.setProductList(products.getContent());
            pagingUtil.setPagingAttributes(model, products, pageNumber, pageSize);
        }
        model.addAttribute(CATEGORY, category);
        return new ModelAndView(CATEGORY_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getHomePageData(int pageNumber, int pageSize) {
        ModelMap model = new ModelMap();
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(ID).ascending());
        Page<Category> categoriesList = categoryRepository.findAll(paging);
        model.addAttribute(CATEGORIES, categoriesList.getContent());
        pagingUtil.setPagingAttributes(model, categoriesList, pageNumber, pageSize);
        return new ModelAndView(HOME_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getSearchPageData() {
        ModelMap model = new ModelMap();
        List<Category> categoriesList = read();
        model.addAttribute(CATEGORIES, categoriesList);
        return new ModelAndView(SEARCH_PRODUCT_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView saveCategoriesFromFile(MultipartFile file) {
        ModelMap model = new ModelMap();
        try {
            List<Category> csvCategories = csvUtil.parseCsvCategory(file);
            if (Optional.ofNullable(csvCategories).isPresent()) {
                categoryRepository.saveAll(csvCategories);
            }
            model.addAttribute(OPERATION_STATUS_SUCCESS, "You saved categories from file " + file.getOriginalFilename());
            log.info("Сategories from file" + file.getOriginalFilename() + "have been saved");
        } catch (Exception e) {
            log.error("Сan`t save categories from file " + file.getOriginalFilename());
            model.addAttribute(OPERATION_STATUS_FAIL, "Сan`t save categories from the file " + file.getOriginalFilename() + ". Check, if such file is correct");
        }
        return new ModelAndView(DATA_MANAGEMENT_PAGE.getPath(), model);
    }

    @Override
    public void downloadCategoriesToCsvFile(HttpServletResponse response) {
        try {
            Writer writer = csvUtil.setResponsePropertiesAndGetWriter(response, CATEGORIES_CSV_FILE_NAME);
            List<Category> categories = read();
            csvUtil.saveCategoriesToCsvFile(writer, categories);
            log.info("Сategories have been downloaded to file name " + CATEGORIES_CSV_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("error in method saveCategoriesToCsvFile " + e.getMessage());
        }
    }
}