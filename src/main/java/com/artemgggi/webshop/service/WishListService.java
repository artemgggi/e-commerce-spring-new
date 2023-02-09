package com.artemgggi.webshop.service;

import com.artemgggi.webshop.dto.WishListItemRepository;
import com.artemgggi.webshop.dto.WishListRepository;
import com.artemgggi.webshop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private WishListItemRepository wishListItemRepository;

    @Autowired
    private ProductService productService;

    public WishList addToWishFirstTime(Long id, String sessionToken) {
        WishList wishlist = new WishList();
        WishListItem item = new WishListItem();
        item.setDate(new Date());
        item.setProduct(productService.getProductById(id));
        wishlist.getItems().add(item);
        wishlist.setSessionToken(sessionToken);
        wishlist.setDate(new Date());
        return wishListRepository.save(wishlist);
    }

    public void addToExistingWishList(Long id, String sessionToken) {
        WishList wishList = wishListRepository.findBySessionToken(sessionToken);
        Product p = productService.getProductById(id);
        Set<WishListItem> items = wishList.getItems();
        WishListItem wishListItem1 = new WishListItem();
        wishListItem1.setDate(new Date());
        wishListItem1.setProduct(p);
        wishList.getItems().add(wishListItem1);
        wishListRepository.saveAndFlush(wishList);
    }

    public WishList getWishListBySessionToken(String sessionToken) {
        return  wishListRepository.findBySessionToken(sessionToken);
    }

    public WishList removeItemWishList(Long id, String sessionToken) {
        WishList WishList = wishListRepository.findBySessionToken(sessionToken);
        Set<WishListItem> items = WishList.getItems();
        WishListItem item = null;
        for(WishListItem item1 : items) {
            if(item1.getId()==id) {
                item = item1;
            }
        }
        items.remove(item);
        wishListItemRepository.delete(item);
        WishList.setItems(items);
        return wishListRepository.save(WishList);
    }

    public void clearWishList(String sessionToken) {
        WishList sh = wishListRepository.findBySessionToken(sessionToken);
        wishListRepository.delete(sh);
    }
}
