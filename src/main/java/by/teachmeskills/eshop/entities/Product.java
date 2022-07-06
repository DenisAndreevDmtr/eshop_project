package by.teachmeskills.eshop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
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

    @ManyToMany(mappedBy = "productsInOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

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