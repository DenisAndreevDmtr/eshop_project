package by.teachmeskills.eshop.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

import com.opencsv.bean.CsvBindByName;

@Getter
@Setter
@ToString

@SuperBuilder
@Entity
@NoArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(name = "name")
    @CsvBindByName(column = "name")
    private String name;
    @Column(name = "rating")
    @CsvBindByName(column = "rating")
    private double rating;
    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Product> productList;
    @OneToOne(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Image image;
}