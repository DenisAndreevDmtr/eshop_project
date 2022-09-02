package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class SearchParamsDto {
    private String searchParametr;
    private String priceTo;
    private String priceFrom;
    private String searchCategory;
    private String typePrices;
    private String orderBy;
}