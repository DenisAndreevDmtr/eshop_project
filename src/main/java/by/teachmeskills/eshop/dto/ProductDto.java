package by.teachmeskills.eshop.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductDto {
    @CsvBindByName(column = "id")
    @Min(value = 1, message = "Min values is 1")
    private int id;
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "description")
    private String description;
    @CsvBindByName(column = "priceBeforeDiscount")
    private BigDecimal priceBeforeDiscount;
    @CsvBindByName(column = "discount")
    private BigDecimal discount;
    @CsvBindByName(column = "price")
    private BigDecimal price;
    @CsvBindByName(column = "rating")
    private double rating;
    @CsvBindByName(column = "categoryId")
    private int categoryId;
}