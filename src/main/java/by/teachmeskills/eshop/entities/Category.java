package by.teachmeskills.eshop.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@ToString

@SuperBuilder
@Entity
@NoArgsConstructor
@Table(name = "category")
public class Category extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "rating")
    private int rating;
    @Column(name = "image_Path")
    private String imageName;
    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Product> productList;
}