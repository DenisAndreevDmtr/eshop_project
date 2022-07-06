package by.teachmeskills.eshop.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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
    @ManyToMany
    @JoinTable(name="order_product", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productsInOrder;

    public Order(int id, BigDecimal priceOrder, LocalDate date, User user) {
        super(id);
        this.priceOrder = priceOrder;
        this.date = date;
        this.user = user;
    }

    public Order(BigDecimal priceOrder, LocalDate date, User user) {
        this.priceOrder = priceOrder;
        this.date = date;
        this.user = user;
    }

    public Order(BigDecimal priceOrder, LocalDate date, User user, List<Product> productsInOrder) {
        this.priceOrder = priceOrder;
        this.date = date;
        this.user = user;
        this.productsInOrder = productsInOrder;
    }

    public List<Product> getProductsInOrder() {
        return productsInOrder;
    }
}