package com.artemgggi.webshop.service;

import com.artemgggi.webshop.dto.WishListItemRepository;
import com.artemgggi.webshop.dto.WishListRepository;
import com.artemgggi.webshop.model.WishList;
import com.artemgggi.webshop.model.WishListItem;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    private final WishListItemRepository wishListItemRepository;

    private final ProductService productService;

    public WishListService(WishListRepository wishListRepository,
                           WishListItemRepository wishListItemRepository,
                           ProductService productService) {
        this.wishListRepository = wishListRepository;
        this.wishListItemRepository = wishListItemRepository;
        this.productService = productService;
    }

    public void addToWishFirstTime(Long id, String sessionToken) {
        WishList wishlist = new WishList();
        WishListItem wishListitem = new WishListItem();
        wishListitem.setDate(new Date());
        wishListitem.setProduct(productService.getProductById(id));
        wishlist.getItems().add(wishListitem);
        wishlist.setSessionToken(sessionToken);
        wishlist.setDate(new Date());
        wishListRepository.save(wishlist);
    }

    public void addToExistWishList(Long id, String sessionToken) {

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
