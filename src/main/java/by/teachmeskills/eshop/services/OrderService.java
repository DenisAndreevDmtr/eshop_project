package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.entities.Order;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public interface OrderService extends BaseService<Order> {
    void downloadOrderToCsvFile(HttpServletResponse response, int id);

    void downloadAllOrdersToCsvFile(HttpServletResponse response);

    ModelAndView deleteOrderById(int id);
}