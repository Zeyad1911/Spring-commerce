package sim.ecommerce.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import sim.ecommerce.DTOs.ProductDTO;
import sim.ecommerce.model.Product;
import sim.ecommerce.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ProductService {
    private final ProductRepository productRepository;


    public Product createProduct(ProductDTO product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("product should have a name");
        }

        if (product.getDescription() == null || product.getDescription().isEmpty()) {
            throw new IllegalArgumentException("product should have a description");
        }

        try {
            Product p = productMapper(product);
            return productRepository.save(p);
        }
        catch (DataAccessException e) {
            throw new RuntimeException("Database connection issue while saving product: " + e.getMessage());
        }
    }

    public Product productMapper(@NotNull ProductDTO product) {
        Product p = new Product();
        p.setDescription(product.getDescription());
        p.setAvailable(true);
        p.setStockQuantity(product.getStockQuantity());
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setImageUrl(product.getImageUrl());
        return p;
    }

    public Product getProduct(long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found")
        );
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public boolean updateProductQuantity(long productID, int quantity) {
            Product p = productRepository.findById(productID).orElseThrow(
                    () -> new RuntimeException("Product not available")
            );
            if (quantity >= 0) {
                p.setStockQuantity(quantity);
                productRepository.save(p);
                return true;
            }
            else {
                throw new IllegalArgumentException("quantity can not be less than zero");
            }

    }

    public Product updateProductPrice(long id, BigDecimal price) {
        Product p = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found")
        );
        if (Integer.parseInt(String.valueOf(price)) > 0) {
            p.setPrice(price);
            return productRepository.save(p);
        }
        else {
            throw new IllegalArgumentException("Price can not be less than or equal zero");
        }
    }

    public Product updateProductName(long id, String name) throws RuntimeException {
        Product p = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found")
        );
        try {
            p.setName(name);
            return productRepository.save(p);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("there is illegal exception " + e.getMessage());
        }
        catch (DataAccessException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    public String deleteProduct(long id) {
        Product p = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found")
        );

         try {
             productRepository.delete(p);
             return "Product is deleted";
         } catch (Exception e) {
             throw new RuntimeException(e.getMessage());
         }

    }
}
