package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.services.CategoryService;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.eshop.utils.PagesPathEnum.DATA_MANAGEMENT_PAGE;

@Log4j
@RestController
@RequestMapping("/home")
public class HomeController {
    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ModelAndView getHomePage(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return categoryService.getHomePageData(pageNumber, pageSize);
    }

    @GetMapping("/files")
    public ModelAndView openUploadCategoriesPage() {
        return new ModelAndView(DATA_MANAGEMENT_PAGE.getPath());
    }
}