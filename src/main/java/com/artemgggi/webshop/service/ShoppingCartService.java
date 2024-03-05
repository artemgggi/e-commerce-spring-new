package com.artemgggi.webshop.service;

import com.artemgggi.webshop.model.cart.CartItem;
import com.artemgggi.webshop.model.product.Product;
import com.artemgggi.webshop.model.cart.ShoppingCart;
import com.artemgggi.webshop.repository.CartItemRepository;
import com.artemgggi.webshop.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class ShoppingCartService {

    private  ShoppingCartRepository shoppingCartRepository;

    private  ProductService productService;

    private  CartItemRepository cartItemRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               ProductService productService,
                               CartItemRepository cartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    public void addShoppingCartFirstTime(Long id, String sessionToken, int quantity) {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setDate(new Date());
        cartItem.setProduct(productService.getProductById(id));
        shoppingCart.getItems().add(cartItem);
        shoppingCart.setSessionToken(sessionToken);
        shoppingCart.setDate(new Date());
        shoppingCartRepository.save(shoppingCart);
    }

    public void addToExistShoppingCart(Long id, String sessionToken, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken);
        Product p = productService.getProductById(id);
        boolean productExistInTheCart = false;
        Set<CartItem> items = shoppingCart.getItems();
        for (CartItem item : items) {
            if (item.getProduct().equals(p)) {
                productExistInTheCart = true;
                item.setQuantity(item.getQuantity() + quantity);
            }
        }
        shoppingCart.setItems(items);
        shoppingCartRepository.saveAndFlush(shoppingCart);
        if (!productExistInTheCart) {
            CartItem cartItem1 = new CartItem();
            cartItem1.setDate(new Date());
            cartItem1.setQuantity(quantity);
            cartItem1.setProduct(p);
            shoppingCart.getItems().add(cartItem1);
            shoppingCartRepository.saveAndFlush(shoppingCart);
        }
    }

    public ShoppingCart getShoppingCartBySessionToken(String sessionToken) {
        return shoppingCartRepository.findBySessionToken(sessionToken);
    }

    public void updateShoppingCartItem(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id).get();
        cartItem.setQuantity(quantity);
        cartItemRepository.saveAndFlush(cartItem);
    }

    public void clearShoppingCart(String sessionToken) {
        ShoppingCart sh = shoppingCartRepository.findBySessionToken(sessionToken);
        shoppingCartRepository.delete(sh);
    }
}

