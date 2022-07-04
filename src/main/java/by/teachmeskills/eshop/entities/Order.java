package by.teachmeskills.eshop.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@Data
public class Order extends BaseEntity {
    private BigDecimal priceOrder;
    private LocalDate date;
    private int userId;
    private List<Product> productsInOrder;

    public Order(int id, BigDecimal priceOrder, LocalDate date, int userId) {
        super(id);
        this.priceOrder = priceOrder;
        this.date = date;
        this.userId = userId;
    }

    public Order(BigDecimal priceOrder, LocalDate date, int userId) {
        this.priceOrder = priceOrder;
        this.date = date;
        this.userId = userId;
    }

    public Order(BigDecimal priceOrder, LocalDate date, int userId, List<Product> productsInOrder) {
        this.priceOrder = priceOrder;
        this.date = date;
        this.userId = userId;
        this.productsInOrder = productsInOrder;
    }

    public List<Product> getProductsInOrder() {
        return productsInOrder;
    }
}