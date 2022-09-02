package by.teachmeskills.eshop.utils;

import by.teachmeskills.eshop.dto.ImageDto;
import by.teachmeskills.eshop.dto.OrderDto;
import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.dto.converters.ImageConverter;
import by.teachmeskills.eshop.dto.converters.OrderConverter;
import by.teachmeskills.eshop.dto.converters.ProductConverter;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Image;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.entities.User;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.eshop.utils.EshopConstants.ATTACHMENT_FILENAME;
import static by.teachmeskills.eshop.utils.EshopConstants.CONTENT_DISPOSITION;
import static by.teachmeskills.eshop.utils.EshopConstants.CONTENT_TYPE;
import static by.teachmeskills.eshop.utils.EshopConstants.ENCODING;

@Log4j
@Component
public class CsvUtil {
    private final ProductConverter productConverter;
    private final ImageConverter imageConverter;
    private final OrderConverter orderConverter;

    public CsvUtil(ProductConverter productConverter, ImageConverter imageConverter, OrderConverter orderConverter) {
        this.productConverter = productConverter;
        this.imageConverter = imageConverter;
        this.orderConverter = orderConverter;
    }

    public Writer setResponsePropertiesAndGetWriter(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        response.addHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + fileName);
        return response.getWriter();
    }

    public List<Category> parseCsvCategory(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<Category> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Category.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build();
                return csvToBean.parse();
            } catch (Exception ex) {
                log.error("Exception occurred during CSV parsing: {} " + ex.getMessage());
            }
        } else {
            log.error("Empty CSV file is uploaded.");
        }
        return Collections.emptyList();
    }

    public void saveCategoriesToCsvFile(Writer writer, List<Category> categories) {
        try {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(categories);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public List<Product> parseCsvProduct(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductDto> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(ProductDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build();
                List<ProductDto> productDtos = csvToBean.parse();
                return Optional.ofNullable(productDtos)
                        .map(list -> list.stream()
                                .map(productConverter::fromDto)
                                .toList())
                        .orElse(null);
            } catch (Exception ex) {
                log.error("Exception occurred during CSV parsing: {} " + ex.getMessage());
            }
        } else {
            log.error("Empty CSV file is uploaded.");
        }
        return Collections.emptyList();
    }

    public void saveProductsToCsvFile(Writer writer, List<Product> products) {
        try {
            List<ProductDto> productDtos = Optional.ofNullable(products)
                    .map(list -> list.stream()
                            .map(productConverter::toDto)
                            .toList())
                    .orElse(null);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(productDtos);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void saveOrderToCsvFile(Writer writer, Order order) {
        try {
            OrderDto orderDto = Optional.ofNullable(orderConverter.toDto(order)).orElse(null);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(orderDto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void saveAllOrdersToCsvFile(Writer writer, List<Order> orders) {
        try {
            List<OrderDto> orderDtos = Optional.ofNullable(orders)
                    .map(list -> list.stream()
                            .map(orderConverter::toDto)
                            .toList())
                    .orElse(null);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(orderDtos);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    public void saveAllUsersToCsvFile(Writer writer, List<User> users) {
        try {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(users);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public List<Image> parseCsvImage(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ImageDto> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(ImageDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build();
                List<ImageDto> imageDtos = csvToBean.parse();
                return Optional.ofNullable(imageDtos)
                        .map(list -> list.stream()
                                .map(imageConverter::fromDto)
                                .toList())
                        .orElse(null);
            } catch (Exception ex) {
                log.error("Exception occurred during CSV parsing: {} " + ex.getMessage());
            }
        } else {
            log.error("Empty CSV file is uploaded.");
        }
        return Collections.emptyList();
    }

    public void saveImagesToCsvFile(Writer writer, List<Image> images) {
        try {
            List<ImageDto> imageDtos = Optional.ofNullable(images)
                    .map(list -> list.stream()
                            .map(imageConverter::toDto)
                            .toList())
                    .orElse(null);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(imageDtos);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}