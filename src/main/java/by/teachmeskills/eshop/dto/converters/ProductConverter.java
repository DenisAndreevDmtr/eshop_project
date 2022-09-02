package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.repositories.ImageRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductConverter {
    private final CategoryRepository categoryRepository;
    private final ImageConverter imageConverter;
    private final ImageRepository imageRepository;

    public ProductConverter(CategoryRepository categoryRepository, ImageConverter imageConverter, ImageRepository imageRepository) {
        this.categoryRepository = categoryRepository;
        this.imageConverter = imageConverter;
        this.imageRepository = imageRepository;
    }

    public ProductDto toDto(Product product) {
        return Optional.ofNullable(product).map(p -> ProductDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .priceBeforeDiscount(p.getPriceBeforeDiscount())
                        .discount(p.getDiscount())
                        .price(p.getPrice())
                        .rating(p.getRating())
                        .categoryId(p.getCategory().getId())
                        .build()).
                orElse(null);
    }

    public Product fromDto(ProductDto productDto) {
        return Optional.ofNullable(productDto).map(pd -> Product.builder()
                        .name(pd.getName())
                        .description(pd.getDescription())
                        .priceBeforeDiscount(pd.getPriceBeforeDiscount())
                        .discount(pd.getDiscount())
                        .price(pd.getPrice())
                        .rating(pd.getRating())
                        .category(categoryRepository.getCategoryById(pd.getCategoryId()))
                        .build()).
                orElse(null);
    }
}