package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.eshop.utils.EshopConstants.SEARCH_PARAMS_DTO;

@SessionAttributes(SEARCH_PARAMS_DTO)
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


    @PostMapping("/result")
    public ModelAndView getSearchResultDataPostMapping(
            @ModelAttribute SearchParamsDto searchParamsDto,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        return productService.searchProducts(searchParamsDto, pageNumber, pageSize);
    }

    @GetMapping("/result")
    public ModelAndView getSearchResultDataGetMapping(
            @ModelAttribute(SEARCH_PARAMS_DTO) SearchParamsDto searchParamsDto,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        return productService.searchProducts(searchParamsDto, pageNumber, pageSize);
    }

    @ModelAttribute(SEARCH_PARAMS_DTO)
    public SearchParamsDto setUpSearchForm() {
        return new SearchParamsDto();
    }
}