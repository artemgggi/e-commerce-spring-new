package com.artemgggi.webshop.service;

import com.artemgggi.webshop.dto.CategoryRepository;
import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Carousel;
import com.artemgggi.webshop.model.Category;
import com.artemgggi.webshop.model.Coupon;
import com.artemgggi.webshop.model.Product;
import jakarta.transaction.Transactional;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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

//    private Product addCategoriesToProduct(Product p ,String categories) {
//        String [] cates = categories.split(",");
//        for(String str : cates) {
//            Category category = categoryRepository.findById(Long.parseLong(str)).get();
//            p.getCategories().add(category);
//        }
//        return p;
//    }

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

    public Category saveCategory(String name) {
        Category c = new Category();
        c.setName(name);
        return categoryRepository.save(c);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public String resizeImageForUse(String src,int width, int height) {
        BufferedImage image = base64ToBufferedImage(src);
        try {
            image = resizeImage(image, width,height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return bufferedImageTobase64(image);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private  BufferedImage resizeImage(BufferedImage image , int width , int height)
            throws IOException {
        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(image).size(width, height)
                    .outputFormat("JPEG").outputQuality(1)
                    .toOutputStream(outputstream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = outputstream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return ImageIO.read(inputStream);
    }

    private BufferedImage base64ToBufferedImage(String base64Img) {
        BufferedImage image = null;
        byte[] decodedBytes = Base64.getDecoder().decode(base64Img);
        try {
            image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private String bufferedImageTobase64(BufferedImage image )
            throws UnsupportedEncodingException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "JPEG", Base64.getEncoder().wrap(out));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString(StandardCharsets.ISO_8859_1);
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
        return productRepository.findProductByCategory(id);
    }
}
