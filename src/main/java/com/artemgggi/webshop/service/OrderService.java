package com.artemgggi.webshop.service;
import com.artemgggi.webshop.dto.OrderDetailRepository;
import com.artemgggi.webshop.dto.OrderRepository;
import com.artemgggi.webshop.dto.ShoppingCartRepository;
import com.artemgggi.webshop.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    final private OrderRepository orderRepository;

    final private OrderDetailRepository orderDetailRepository;

    final private ShoppingCartRepository shoppingCartRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository,
                        ShoppingCartRepository shoppingCartRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public void saveOrder(String sessionToken) {
        ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken);
        Order order = new Order();
        Customer customer = new Customer();
        order.setOrderStatus("PENDING");
        order.setOrderDate(new Date());
        order.setCustomer(customer);
        order.setTotalPrice(shoppingCart.getTotalPrice());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem item : shoppingCart.getItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setProduct(item.getProduct());
            orderDetail.setUnitPrice(item.getProduct().getPrice());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
        }
        order.setOrderDetailList(orderDetailList);
        orderRepository.save(order);
        }
}

