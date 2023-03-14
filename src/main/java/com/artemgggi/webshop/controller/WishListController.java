package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.model.WishList;
import com.artemgggi.webshop.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    public String getSessionToken(HttpServletRequest request) {
        return (String) request.getSession(true).getAttribute("sessionTokenWishList");
    }

    @GetMapping("/wishList")
    public String getWishList(HttpServletRequest request, Model model) {
        String sessionTokenWishList = getSessionToken(request);
        if (sessionTokenWishList == null) {
            model.addAttribute("wishList", new WishList());
        } else {
            WishList wishList = wishListService.getShoppingCartBySessionToken(sessionTokenWishList);
            model.addAttribute("wishList", wishList);
        }
        return "/wishList";
    }

    @GetMapping("/addToWishlist/{id}")
    public String addToWishList(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionTokenWishList= getSessionToken(request);
        if (sessionTokenWishList == null) {
            sessionTokenWishList = UUID.randomUUID().toString();
            request.getSession().setAttribute("sessionTokenWishList", sessionTokenWishList);
            wishListService.addToWishListFirstTime(id, sessionTokenWishList);
        } else {
            wishListService.addToExistWishList(id, sessionTokenWishList);
        }
        return "redirect:/";
    }

    @GetMapping("/removeWishListItem/{id}")
    public String removeItem(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionTokenWishList");
        wishListService.removeItemWishList(id, sessionToken);
        return "redirect:/wishList";
    }

    @GetMapping("/clearWishList")
    public String clearShoopiString(HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionTokenWishList");
        request.getSession(false).removeAttribute("sessionTokenWishList");
        wishListService.clearWishList(sessionToken);
        return "redirect:/wishList";
    }
}
