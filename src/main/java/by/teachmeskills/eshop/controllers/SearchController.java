package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.eshop.utils.EshopConstants.SEARCH_PARAM;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public SearchController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView openSearchPage() {
        return categoryService.getSearchPageData();
    }

    @PostMapping
    public ModelAndView getSearchResult(@RequestParam(SEARCH_PARAM) String searchParametr) {
        return productService.getSearchResult(searchParametr);
    }
}