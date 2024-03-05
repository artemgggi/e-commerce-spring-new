package com.artemgggi.webshop.service;
import com.artemgggi.webshop.model.cart.CartItem;
import com.artemgggi.webshop.model.cart.ShoppingCart;
import com.artemgggi.webshop.model.order.Order;
import com.artemgggi.webshop.model.order.OrderDetail;
import com.artemgggi.webshop.model.user.User;
import com.artemgggi.webshop.repository.OrderDetailRepository;
import com.artemgggi.webshop.repository.OrderRepository;
import com.artemgggi.webshop.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

     private OrderRepository orderRepository;

     private OrderDetailRepository orderDetailRepository;

     private ShoppingCartRepository shoppingCartRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository,
                        ShoppingCartRepository shoppingCartRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public void placeOrder(String sessionToken) {
        ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken);
        Order order = new Order();
        User user = new User();
        order.setOrderStatus("PENDING");
        order.setOrderDate(new Date());
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

