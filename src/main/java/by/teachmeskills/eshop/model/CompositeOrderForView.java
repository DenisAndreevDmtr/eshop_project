package by.teachmeskills.eshop.model;

import by.teachmeskills.eshop.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor

public class CompositeOrderForView {
    private int idOrder;
    private BigDecimal sumOrder;
    private LocalDate date;
    private Map<Product, Long> productAndAmount;
}