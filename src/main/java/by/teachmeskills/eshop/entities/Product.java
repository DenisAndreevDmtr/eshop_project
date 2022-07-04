package by.teachmeskills.eshop.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@Data
public class Product extends BaseEntity {
    private String name;
    private String imageName;
    private String description;
    private BigDecimal price;
    private int categoryId;

    public Product(int id, String name, String imageName, String description, BigDecimal price, int categoryId) {
        super(id);
        this.name = name;
        this.imageName = imageName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Product(String name, String imageName, String description, BigDecimal price, int categoryId) {
        this.name = name;
        this.imageName = imageName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }
}