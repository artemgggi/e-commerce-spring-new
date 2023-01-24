package com.artemgggi.webshop.service;

import com.artemgggi.webshop.dto.ShoppingCartRepository;
import com.artemgggi.webshop.model.CartItem;
import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductService productService;

    public ShoppingCart addShopingCartFirstTime(Long id, String sessionToken, int quantity) {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setDate(new Date());
        cartItem.setProduct(productService.getProductById(id));
        shoppingCart.getItems().add(cartItem);
        shoppingCart.setTokenSession(sessionToken);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart addToExistShoppingCart(Long id, String sessionToken, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByTokenSession(sessionToken);
        Product p = productService.getProductById(id);
        for (CartItem item : shoppingCart.getItems()) {
            if (p.getId().equals(item.getProduct().getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return shoppingCartRepository.save(shoppingCart);
            }
        }
        CartItem cartItem = new CartItem();
        cartItem.setDate(new Date());
        cartItem.setQuantity(quantity);
        cartItem.setProduct(p);
        shoppingCart.getItems().add(cartItem);
        return shoppingCartRepository.save(shoppingCart);
    }
}

