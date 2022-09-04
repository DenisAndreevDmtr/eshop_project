package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.ProductSearchSpecification;
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

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.eshop.utils.EshopConstants.CATEGORIES;
import static by.teachmeskills.eshop.utils.EshopConstants.DISCOUNT;
import static by.teachmeskills.eshop.utils.EshopConstants.NAME;
import static by.teachmeskills.eshop.utils.EshopConstants.OPERATION_STATUS_FAIL;
import static by.teachmeskills.eshop.utils.EshopConstants.OPERATION_STATUS_SUCCESS;
import static by.teachmeskills.eshop.utils.EshopConstants.ORDER_BY;
import static by.teachmeskills.eshop.utils.EshopConstants.ORDER_BY_DISCOUNT;
import static by.teachmeskills.eshop.utils.EshopConstants.ORDER_BY_NAME_ASC;
import static by.teachmeskills.eshop.utils.EshopConstants.ORDER_BY_NAME_DESC;
import static by.teachmeskills.eshop.utils.EshopConstants.ORDER_BY_PRICE_ASC;
import static by.teachmeskills.eshop.utils.EshopConstants.ORDER_BY_PRICE_DESC;
import static by.teachmeskills.eshop.utils.EshopConstants.ORDER_BY_RATING;
import static by.teachmeskills.eshop.utils.EshopConstants.PRICE;
import static by.teachmeskills.eshop.utils.EshopConstants.PRICE_FROM;
import static by.teachmeskills.eshop.utils.EshopConstants.PRICE_TO;
import static by.teachmeskills.eshop.utils.EshopConstants.PRODUCT;
import static by.teachmeskills.eshop.utils.EshopConstants.PRODUCTS;
import static by.teachmeskills.eshop.utils.EshopConstants.PRODUCTS_CSV_FILE_NAME;
import static by.teachmeskills.eshop.utils.EshopConstants.RATING;
import static by.teachmeskills.eshop.utils.EshopConstants.SEARCH_CATEGORY;
import static by.teachmeskills.eshop.utils.EshopConstants.SEARCH_PARAM;
import static by.teachmeskills.eshop.utils.EshopConstants.SEARCH_RESULT;
import static by.teachmeskills.eshop.utils.EshopConstants.TYPE_PRICES;
import static by.teachmeskills.eshop.utils.PagesPathEnum.DATA_MANAGEMENT_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.HOT_PRICES;
import static by.teachmeskills.eshop.utils.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.SEARCH_PRODUCT_PAGE;

@Service
@Log4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CsvUtil csvUtil;
    private final PagingUtil pagingUtil;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, CsvUtil csvUtil, PagingUtil pagingUtil) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.csvUtil = csvUtil;
        this.pagingUtil = pagingUtil;
    }

    @Override
    public Product create(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public List<Product> read() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public ModelAndView deleteProductById(int id) {
        ModelMap model = new ModelMap();
        try {
            productRepository.deleteById(id);
            model.addAttribute(OPERATION_STATUS_SUCCESS, "You deleted product with id № " + id);
            log.info("Product with id " + id + " was deleted");
        } catch (Exception e) {
            log.error("Сan`t delete product with id " + id);
            model.addAttribute(OPERATION_STATUS_FAIL, "Сan`t delete product with id " + id + ". Check, if such id is exist");
        }
        return new ModelAndView(DATA_MANAGEMENT_PAGE.getPath(), model);
    }

    @Override
    public Page<Product> getAllProductsByCategory(int categoryId, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        return productRepository.findAllByCategoryId(categoryId, paging);
    }

    @Override
    public ModelAndView getProductData(int id) {
        ModelMap model = new ModelMap();
        Product product = productRepository.getProductById(id);
        if (Optional.ofNullable(product).isPresent()) {
            model.addAttribute(PRODUCT, product);
        }
        return new ModelAndView(PRODUCT_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getHotPricesProducts(int pageNumber, int pageSize) {
        ModelMap model = new ModelMap();
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(DISCOUNT).descending());
        Page<Product> products = productRepository.findProductsByDiscountGreaterThan(BigDecimal.ZERO, paging);
        if (Optional.ofNullable(products).isPresent()) {
            model.addAttribute(PRODUCTS, products.getContent());
            pagingUtil.setPagingAttributes(model, products, pageNumber, pageSize);
        }
        return new ModelAndView(HOT_PRICES.getPath(), model);
    }

    @Override
    public ModelAndView saveProductsFromFile(MultipartFile file) {
        ModelMap model = new ModelMap();
        try {
            List<Product> csvProducts = csvUtil.parseCsvProduct(file);
            if (Optional.ofNullable(csvProducts).isPresent() && csvProducts.size() > 0) {
                productRepository.saveAll(csvProducts);
                model.addAttribute(OPERATION_STATUS_SUCCESS, "You saved products from file " + file.getOriginalFilename());
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            log.error("Сan`t save products from file " + file.getOriginalFilename());
            model.addAttribute(OPERATION_STATUS_FAIL, "Сan`t save products from the file " + file.getOriginalFilename() + ". Check, if such file is correct");
        }
        return new ModelAndView(DATA_MANAGEMENT_PAGE.getPath(), model);
    }

    @Override
    public void downloadProductsToCsvFile(HttpServletResponse response) {
        try {
            Writer writer = csvUtil.setResponsePropertiesAndGetWriter(response, PRODUCTS_CSV_FILE_NAME);
            List<Product> products = read();
            csvUtil.saveProductsToCsvFile(writer, products);
            log.info("Products have been downloaded to file name " + PRODUCTS_CSV_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("error in method saveProductsToCsvFile " + e.getMessage());
        }
    }

    @Override
    public ModelAndView searchProducts(SearchParamsDto searchParamsDto, int pageNumber, int pageSize) {
        ModelMap model = new ModelMap();
        List<Category> categoriesList = categoryRepository.findAll();
        model.addAttribute(CATEGORIES, categoriesList);
        Pageable paging = getPage(searchParamsDto, pageNumber, pageSize);
        ProductSearchSpecification productSearchSpecification = new ProductSearchSpecification(searchParamsDto);
        Page<Product> requestProducts = productRepository.findAll(productSearchSpecification, paging);
        pagingUtil.setPagingAttributes(model, requestProducts, pageNumber, pageSize);
        List<Product> allProductsBySearch = productRepository.findAll(productSearchSpecification);
        model.addAttribute(PRICE_FROM, getMinPrice(allProductsBySearch, checkString(searchParamsDto.getPriceFrom())));
        model.addAttribute(PRICE_TO, getMaxPrice(allProductsBySearch, checkString(searchParamsDto.getPriceTo())));
        model.addAttribute(SEARCH_PARAM, searchParamsDto.getSearchParametr());
        model.addAttribute(SEARCH_CATEGORY, searchParamsDto.getSearchCategory());
        model.addAttribute(TYPE_PRICES, searchParamsDto.getTypePrices());
        model.addAttribute(ORDER_BY, searchParamsDto.getOrderBy());
        model.addAttribute(SEARCH_RESULT, requestProducts.getContent());
        return new ModelAndView(SEARCH_PRODUCT_PAGE.getPath(), model);
    }

    private Pageable getPage(SearchParamsDto searchParamsDto, int pageNumber, int pageSize) {
        return switch (searchParamsDto.getOrderBy()) {
            case ORDER_BY_PRICE_ASC -> PageRequest.of(pageNumber, pageSize, Sort.by(PRICE).ascending());
            case ORDER_BY_PRICE_DESC -> PageRequest.of(pageNumber, pageSize, Sort.by(PRICE).descending());
            case ORDER_BY_NAME_ASC -> PageRequest.of(pageNumber, pageSize, Sort.by(NAME).ascending());
            case ORDER_BY_NAME_DESC -> PageRequest.of(pageNumber, pageSize, Sort.by(NAME).descending());
            case ORDER_BY_RATING -> PageRequest.of(pageNumber, pageSize, Sort.by(RATING).descending());
            case ORDER_BY_DISCOUNT -> PageRequest.of(pageNumber, pageSize, Sort.by(DISCOUNT).descending());
            default -> PageRequest.of(pageNumber, pageSize, Sort.by(NAME).ascending());
        };
    }

    private int getMinPrice(List<Product> products, int priceValue) {
        if (priceValue == 0) {
            Optional<BigDecimal> min = products.stream().map(Product::getPrice).min(Comparator.naturalOrder());
            if (min.isPresent()) {
                return min.get().intValue();
            }
        }
        return priceValue;
    }

    private int getMaxPrice(List<Product> products, int priceValue) {
        if (priceValue == 0) {
            Optional<BigDecimal> max = products.stream().map(Product::getPrice).max(Comparator.naturalOrder());
            if (max.isPresent()) {
                return max.get().intValue();
            }
        }
        return priceValue;
    }

    private int checkString(String str) {
        if (str.isBlank()) {
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }
}