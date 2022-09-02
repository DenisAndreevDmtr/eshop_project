package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.OrderDto;
import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.repositories.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import java.util.stream.Collectors;

@Log4j
@Component
public class OrderConverter {
    private final OrderRepository orderRepository;
    private final ProductConverter productConverter;
    private final UserRepository userRepository;

    public OrderConverter(OrderRepository orderRepository, ProductConverter productConverter, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productConverter = productConverter;
        this.userRepository = userRepository;
    }

    public OrderDto toDto(Order order) {
        Map<ProductDto, Integer> productsDto = order.getProducts().entrySet().stream().collect(Collectors.toMap(x -> productConverter.toDto(x.getKey()), Map.Entry::getValue));
        return Optional.ofNullable(order).map(o -> OrderDto.builder()
                        .id(o.getId())
                        .priceOrder(o.getPriceOrder())
                        .dateCreation(o.getDateCreation())
                        .userId(o.getUser().getId())
                        .products(productsDto)
                        .build()).
                orElse(null);
    }

    public Order fromDto(OrderDto orderDto) {
        return Optional.ofNullable(orderDto).map(od -> Order.builder()
                        .id(od.getId())
                        .dateCreation(od.getDateCreation())
                        .priceOrder(od.getPriceOrder())
                        .user(userRepository.findById(od.getUserId()).get())
                        .products(orderRepository.getOrderById(od.getId()).getProducts())
                        .build())
                .orElse(null);
    }
}