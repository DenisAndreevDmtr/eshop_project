package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.services.OrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Log4j
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/ordersdownload")
    public void getOrdersToScvFile(HttpServletResponse response) {
        orderService.downloadAllOrdersToCsvFile(response);
    }

    @GetMapping("/download")
    public void getOrderByIdToScvFile(@RequestParam(defaultValue = "1") int orderId, HttpServletResponse response) {
        orderService.downloadOrderToCsvFile(response, orderId);
    }

    @PostMapping("/delete")
    public ModelAndView deleteOrderById(int orderIdDelete) {
        return orderService.deleteOrderById(orderIdDelete);
    }
}