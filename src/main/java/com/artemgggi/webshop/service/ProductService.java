package com.artemgggi.webshop.service;

import com.artemgggi.webshop.dto.CategoryRepository;
import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Category;
import com.artemgggi.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Objects;

import net.coobird.thumbnailator.Thumbnails;

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
            p.setImage(resizeImageForUse(Base64.getEncoder()
                    .encodeToString(file.getBytes()), 400, 400).getBytes());
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

    public String resizeImageForUse(String src,int width, int height) {
        BufferedImage image = base64ToBufferedImage(src);
        try {
            image = resizeImage(image, width,height);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            return bufferedImageTobase64(image);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    private  BufferedImage resizeImage(BufferedImage image , int width , int height) throws IOException {
        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(image).size(width, height)
                    .outputFormat("JPEG").outputQuality(1).toOutputStream(outputstream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return image;
    }

    private String bufferedImageTobase64(BufferedImage image ) throws UnsupportedEncodingException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "JPEG", Base64.getEncoder().wrap(out));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return out.toString(StandardCharsets.ISO_8859_1.name());
    }
}
