package com.artemgggi.webshop.service;

import com.artemgggi.webshop.dto.CategoryRepository;
import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Carousel;
import com.artemgggi.webshop.model.Category;
import com.artemgggi.webshop.model.Coupon;
import com.artemgggi.webshop.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void saveProductToDB(MultipartFile file, String name, int price,
                                String description, int quantity, String categories) {
        Product p = new Product();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(
                file.getOriginalFilename()));
        if (fileName.contains("..")) {
            System.out.println("not valid image");
        }
        try {
            p.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Category category = categoryRepository.findById(Long.parseLong(categories)).get();
        p.setName(name);
        p.setPrice(price);
        p.setDescription(description);
        p.setQuantity(quantity);
        p.setCategory(category);
        Coupon c = new Coupon();
        c.setDiscount(0);
        p.setDiscount(c);
        productRepository.save(p);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public void changeProductName(Long id ,String name) {
        Product p = productRepository.findById(id).get();
        p.setName(name);
        productRepository.save(p);
    }

    public void changeProductDescription(Long id , String description) {
        Product p = productRepository.findById(id).get();
        p.setDescription(description);
        productRepository.save(p);
    }

    public void changeProductPrice(Long id,int price) {
        Product p = productRepository.findById(id).get();
        p.setPrice(price);
        productRepository.save(p);
    }

    public void saveCategory(String name) {
        Category c = new Category();
        c.setName(name);
        categoryRepository.save(c);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addImageToProduct(MultipartFile file, Long id) {
        Product p = productRepository.findById(id).get();
        Carousel carousel = new Carousel();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a valid file");
        }
        try {
            p.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            carousel.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.getCarousel().add(carousel);
        productRepository.save(p);
    }

    public void addDiscountToProduct(Long id, int discount) {
        Product p = productRepository.findById(id).get();
        if (p.getDiscount() == null) {
            Coupon c = new Coupon();
            c.setDiscount(discount);
            p.setDiscount(c);
        } else {
            p.getDiscount().setDiscount(discount);
        }
        productRepository.save(p);
    }

    public void changeProductQuantity(Long id, int quantity) {
        Product p = productRepository.findById(id).get();
        p.setQuantity(quantity);
        productRepository.save(p);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Transactional
    public List<Product> searchProductByNameLike(String value) {
        return productRepository.findByNameContainingIgnoreCase(value);
    }

    @Transactional
    public List<Product> searchProductByCategory(String id) {
        return productRepository.findProductByCategoryId(id);
    }
}
