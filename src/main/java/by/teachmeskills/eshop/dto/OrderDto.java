package by.teachmeskills.eshop.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @CsvBindByName(column = "id")
    private int id;
    @CsvBindByName(column = "priceOrder")
    private BigDecimal priceOrder;
    @CsvBindByName(column = "dateCreation")
    private LocalDate dateCreation;
    @CsvBindByName(column = "products")
    private Map<ProductDto, Integer> products;
    @CsvBindByName(column = "userId")
    private int userId;

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", priceOrder=" + priceOrder +
                ", dateCreation=" + dateCreation +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return id == orderDto.id && userId == orderDto.userId && Objects.equals(priceOrder, orderDto.priceOrder) && Objects.equals(dateCreation, orderDto.dateCreation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priceOrder, dateCreation, userId);
    }
}