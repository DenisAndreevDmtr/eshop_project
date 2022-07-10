package by.teachmeskills.eshop.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@Data
@NoArgsConstructor
@Entity
@Table(name = "internet_shop.order")
public class Order extends BaseEntity {
    @Column(name = "price")
    private BigDecimal priceOrder;
    @Column(name = "date_Order")
    private LocalDate date;
    @ManyToOne
    private User user;
    @ElementCollection
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "product_amount", nullable = false)
    private Map<Product, Integer> products = new HashMap<>();

    public Map<Product, Integer> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    public void addProducts(Product product, int quantity) {
        products.merge(product, quantity, Integer::sum);
    }

    public void removeItem(Product product) {
        products.computeIfPresent(product, (k, v) -> v > 1 ? v - 1 : null);
    }

    public Order(BigDecimal priceOrder, LocalDate date, User user, Map<Product, Integer> products) {
        this.priceOrder = priceOrder;
        this.date = date;
        this.user = user;
        this.products = products;
    }

    public Order(int id, BigDecimal priceOrder, LocalDate date, User user, Map<Product, Integer> products) {
        super(id);
        this.priceOrder = priceOrder;
        this.date = date;
        this.user = user;
        this.products = products;
    }
}