package com.artemgggi.webshop.service;

import com.artemgggi.webshop.model.product.Product;
import com.artemgggi.webshop.model.wishlist.WishList;
import com.artemgggi.webshop.model.wishlist.WishListItem;
import com.artemgggi.webshop.repository.WishListItemRepository;
import com.artemgggi.webshop.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
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

    public WishList getWishListBySessionToken(String sessionTokenWishList) {
        return wishListRepository.findBySessionToken(sessionTokenWishList);
    }

    public void addToWishListFirstTime(Long id, String sessionTokenWishList) {
        WishList wishlist = new WishList();
        WishListItem wishListitem = new WishListItem();
        wishListitem.setDate(new Date());
        wishListitem.setProduct(productService.getProductById(id));
        wishlist.getItems().add(wishListitem);
        wishlist.setSessionToken(sessionTokenWishList);
        wishlist.setDate(new Date());
        wishListRepository.save(wishlist);
    }

    public void removeItemWishList(Long id, String sessionToken) {
        WishList wishList = wishListRepository.findBySessionToken(sessionToken);
        Set<WishListItem> items = wishList.getItems();
        WishListItem item = null;
        for (WishListItem item1 : items) {
            if (Objects.equals(item1.getId(), id)) {
                item = item1;
            }
        }
        items.remove(item);
        wishListItemRepository.delete(item);
        wishList.setItems(items);
        wishListRepository.save(wishList);
    }

    public void clearWishList(String sessionTokenWishList) {
        WishList sh = wishListRepository.findBySessionToken(sessionTokenWishList);
        wishListRepository.delete(sh);
    }

    public WishList getShoppingCartBySessionToken(String sessionTokenWishList) {
        return wishListRepository.findBySessionToken(sessionTokenWishList);
    }
}
