package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.services.ProductService;
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
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ModelAndView openProductPage(@PathVariable int id) {
        return productService.getProductData(id);
    }

    @PostMapping("/upload")
    public ModelAndView uploadProductsFromFile(@RequestParam(FILE) MultipartFile file) {
        return productService.saveProductsFromFile(file);
    }

    @GetMapping("/download")
    public void downloadProductsToCsvFile(HttpServletResponse response) {
        productService.downloadProductsToCsvFile(response);
    }

    @PostMapping("/delete")
    public ModelAndView deleteProductById(int productId) {
        return productService.deleteProductById(productId);
    }

    @GetMapping("/hotprices")
    public ModelAndView openHotPricesProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        return productService.getHotPricesProducts(pageNumber, pageSize);
    }
}