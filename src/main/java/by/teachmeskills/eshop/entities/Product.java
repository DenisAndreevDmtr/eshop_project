package by.teachmeskills.eshop.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "product")
public class Product extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "image_Path")
    private String imageName;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product(int id, String name, String imageName, String description, BigDecimal price, Category category) {
        super(id);
        this.name = name;
        this.imageName = imageName;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Product(String name, String imageName, String description, BigDecimal price, Category category) {
        this.name = name;
        this.imageName = imageName;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}