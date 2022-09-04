package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.services.OrderService;
import by.teachmeskills.eshop.utils.CsvUtil;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static by.teachmeskills.eshop.utils.EshopConstants.OPERATION_STATUS_FAIL;
import static by.teachmeskills.eshop.utils.EshopConstants.OPERATION_STATUS_SUCCESS;
import static by.teachmeskills.eshop.utils.EshopConstants.ORDERS_CSV_FILE_NAME;
import static by.teachmeskills.eshop.utils.PagesPathEnum.DATA_MANAGEMENT_PAGE;

@Log4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CsvUtil csvUtil;

    public OrderServiceImpl(OrderRepository orderRepository, CsvUtil csvUtil) {
        this.orderRepository = orderRepository;
        this.csvUtil = csvUtil;
    }

    @Override
    public Order create(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public List<Order> read() {
        return orderRepository.findAll();
    }

    @Override
    public Order update(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public void delete(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public ModelAndView deleteOrderById(int id) {
        ModelMap model = new ModelMap();
        try {
            Order order = orderRepository.getOrderById(id);
            orderRepository.deleteById(order.getId());
            model.addAttribute(OPERATION_STATUS_SUCCESS, "You deleted order with id № " + id);
            log.info("Order with id " + id + " was deleted");
        } catch (Exception e) {
            log.error("Сan`t delete order with id " + id);
            model.addAttribute(OPERATION_STATUS_FAIL, "Сan`t delete order with id " + id + ". Check, if such id is exist");
        }
        return new ModelAndView(DATA_MANAGEMENT_PAGE.getPath(), model);
    }

    @Override
    public void downloadOrderToCsvFile(HttpServletResponse response, int id) {
        try {
            Writer writer = csvUtil.setResponsePropertiesAndGetWriter(response, "oder №" + id + ".csv");
            Order order = orderRepository.getOrderById(id);
            csvUtil.saveOrderToCsvFile(writer, order);
            log.info("Order with id " + id + " has been downloaded to file name oder №" + id + ".csv");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("error in method saveOrderToCsvFile " + e.getMessage());
        }
    }

    @Override
    public void downloadAllOrdersToCsvFile(HttpServletResponse response) {
        try {
            Writer writer = csvUtil.setResponsePropertiesAndGetWriter(response, ORDERS_CSV_FILE_NAME);
            List<Order> orders = read();
            csvUtil.saveAllOrdersToCsvFile(writer, orders);
            log.info("Orders have been downloaded to file name oder №" + ORDERS_CSV_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("error in method saveOrderToCsvFile " + e.getMessage());
        }
    }
}