package com.artemgggi.webshop.service;

import com.artemgggi.webshop.dto.CategoryRepository;
import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Category;
import com.artemgggi.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public void saveProductToDB(MultipartFile file, String name, int price,
                                String description) {
        Product p = new Product();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(
                file.getOriginalFilename()));
        if (fileName.contains("..")) {
            System.out.println("not valid image");
        }
        try {
            p.setImage(Base64.getEncoder().encodeToString(file.getBytes()).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        p.setName(name);
        p.setPrice(price);
        p.setDescription(description);
        productRepository.save(p);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
