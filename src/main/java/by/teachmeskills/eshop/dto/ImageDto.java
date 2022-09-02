package by.teachmeskills.eshop.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ImageDto {
    @CsvBindByName(column = "id")
    private int id;
    @CsvBindByName(column = "primaryFlag")
    private boolean primaryFlag;
    @CsvBindByName(column = "imagePath")
    private String imagePath;
    @CsvBindByName(column = "categoryId")
    private int categoryId;
    @CsvBindByName(column = "productId")
    private int productId;
}