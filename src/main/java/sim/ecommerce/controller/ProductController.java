package sim.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sim.ecommerce.DTOs.ProductDTO;
import sim.ecommerce.mappers.ProductDTOMapper;
import sim.ecommerce.model.Product;
import sim.ecommerce.service.ProductService;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.logging.Logger;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductDTOMapper productDTOMapper;
    private Logger logger = Logger.getLogger(ProductController.class.getName());

    public ProductController(ProductService productService, ProductDTOMapper productDTOMapper) {
        this.productService = productService;
        this.productDTOMapper = productDTOMapper;
    }

    @PostMapping("/createProduct")
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
        System.out.println(productDTO.getPrice());
        try {
            productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("A product created");
        } catch (Exception e) {
            throw new RuntimeException("there is a problem while creating a product " + e.getMessage());
        }
    }
    //todo public read;
    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable long id) {
            Product p = productService.getProduct(id);
            if (p == null) {
                return ResponseEntity.notFound().build();
            }
            ProductDTO product = productDTOMapper.productDTOMap(p);
            return ResponseEntity.ok().body(product);
    }

    public ResponseEntity<String> updateProductPrice(long id, BigDecimal price) {
        Product updatedProduct = productService.updateProductPrice(id, price);
        if (Objects.equals(updatedProduct.getPrice(), price)) {
            return ResponseEntity.status(HttpStatus.OK).body("Price updated successfully");
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Issue during updating the price");
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@RequestParam long id) {
            try {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.deleteProduct(id));
            }
            catch (SecurityException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }

    }
}
