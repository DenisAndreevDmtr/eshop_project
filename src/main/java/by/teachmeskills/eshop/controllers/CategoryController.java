package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.services.CategoryService;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import static by.teachmeskills.eshop.utils.EshopConstants.FILE;

@Log4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ModelAndView openCategoryPage(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        return categoryService.getCategoryData(id, pageNumber, pageSize);
    }

    @PostMapping("/upload")
    public ModelAndView uploadCategoriesFromFile(@RequestParam(FILE) MultipartFile file) {
        return categoryService.saveCategoriesFromFile(file);
    }

    @GetMapping("/download")
    public void downloadCategoriesToCsvFile(HttpServletResponse response) {
        categoryService.downloadCategoriesToCsvFile(response);
    }

    @PostMapping("/delete")
    public ModelAndView deleteCategoryById(int categoryId) {
        return categoryService.deleteCategoryById(categoryId);
    }
}