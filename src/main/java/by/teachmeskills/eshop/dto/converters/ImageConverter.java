package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.ImageDto;
import by.teachmeskills.eshop.entities.Image;

import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.repositories.ImageRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j
@Component
public class ImageConverter {
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ImageConverter(ImageRepository imageRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.imageRepository = imageRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public ImageDto toDto(Image image) {
        ImageDto imageDto = ImageDto.builder()
                .imagePath(image.getImagePath())
                .primaryFlag(image.isPrimaryFlag())
                .id(image.getId())
                .build();
        if (image.getCategory() != null) {
            imageDto.setCategoryId(image.getCategory().getId());
        } else {
            imageDto.setProductId(image.getProduct().getId());
        }
        return imageDto;
    }

    public Image fromDto(ImageDto imageDto) {
        checkExistCategory(imageDto);
        checkExistProduct(imageDto);
        return Image.builder()
                .imagePath(imageDto.getImagePath())
                .category(categoryRepository.getCategoryById(imageDto.getCategoryId()))
                .product(productRepository.getProductById(imageDto.getProductId()))
                .primaryFlag(imageDto.isPrimaryFlag())
                .build();
    }

    private ImageDto checkExistCategory(ImageDto imageDto) {
        Image image = imageRepository.findImageByCategory_Id(imageDto.getCategoryId());
        if (Optional.ofNullable(image).isPresent()) {
            imageRepository.deleteById(image.getId());
            imageDto.setPrimaryFlag(true);
        }
        return imageDto;
    }

    private ImageDto checkExistProduct(ImageDto imageDto) {
        Product product = productRepository.getProductById(imageDto.getProductId());
        if (Optional.ofNullable(product).isPresent()) {
            return imageDto;
        }
        log.error("Cant find product with such id № " + imageDto.getProductId());
        return null;
    }
}