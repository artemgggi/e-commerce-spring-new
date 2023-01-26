package com.artemgggi.webshop.service;

import com.artemgggi.webshop.dto.ShoppingCartRepository;
import com.artemgggi.webshop.model.CartItem;
import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductService productService;

    public void addShoppingCartFirstTime(Long id, String sessionToken, int quantity) {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setDate(new Date());
        cartItem.setProduct(productService.getProductById(id));
        shoppingCart.getItems().add(cartItem);
        shoppingCart.setTokenSession(sessionToken);
        shoppingCartRepository.save(shoppingCart);
    }

    public void addToExistShoppingCart(Long id, String sessionToken, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByTokenSession(sessionToken);
        Product p = productService.getProductById(id);
        if (shoppingCart != null) {
            Set<CartItem> items = shoppingCart.getItems();
            for (CartItem item : items) {
                if (item.getProduct().equals(p)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    shoppingCart.setItems(items);
                    shoppingCartRepository.saveAndFlush(shoppingCart);
                }
            }
        }
        if (shoppingCart != null) {
            CartItem cartItem1 = new CartItem();
            cartItem1.setDate(new Date());
            cartItem1.setQuantity(quantity);
            cartItem1.setProduct(p);
            shoppingCart.getItems().add(cartItem1);
            shoppingCartRepository.saveAndFlush(shoppingCart);
        }
        this.addShoppingCartFirstTime(id, sessionToken, quantity);
    }

    public ShoppingCart getShoppingCartBySessionToken(String sessionToken) {
        return shoppingCartRepository.findByTokenSession(sessionToken);
    }
}

