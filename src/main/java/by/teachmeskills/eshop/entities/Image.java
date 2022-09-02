package by.teachmeskills.eshop.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@SuperBuilder
@Table(name = "images")
public class Image extends BaseEntity {
    @Column(name = "primary_flag")
    private boolean primaryFlag;
    @Column(name = "image_path")
    private String imagePath;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product product;
    @OneToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Image image = (Image) o;
        return primaryFlag == image.primaryFlag && Objects.equals(imagePath, image.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), primaryFlag, imagePath);
    }

    public String getImagePath() {
        return imagePath;
    }
}