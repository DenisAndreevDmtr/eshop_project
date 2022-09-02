package by.teachmeskills.eshop.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static by.teachmeskills.eshop.utils.EshopConstants.CRITERIA_GOOD_RATING;

@Log4j
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price_before_discount")
    private BigDecimal priceBeforeDiscount;
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "rating")
    private double rating;
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    List<Image> images;


    //method modifier is public for usage in html page, cause private modifier generates error at page
    public String getMainImage() {
        return images.stream().filter(Image::isPrimaryFlag).findFirst().map(Image::getImagePath).orElse(null);
    }

    //method modifier is public for usage in html page, cause private modifier generates error at page
    public List<String> getListOfParametrsDescription() {
        try {
            String[] splitString = description.split(";");
            return Arrays.asList(splitString);
        } catch (Exception e) {
            log.error("Cant split description of the product id № " + getId());
            return null;
        }
    }

    //method modifier is public for usage in html page, cause private modifier generates error at page
    public boolean isDiscounted() {
        return discount.compareTo(BigDecimal.ZERO) > 0;
    }

    //method modifier is public for usage in html page, cause private modifier generates error at page
    public boolean isGoodRating() {
        return rating >= CRITERIA_GOOD_RATING;
    }
}