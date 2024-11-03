package sim.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sim.ecommerce.DTOs.ProductDTO;
import sim.ecommerce.service.ProductService;

import java.util.function.Supplier;
import java.util.logging.Logger;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private Logger logger = Logger.getLogger(ProductController.class.getName());

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //todo public create;
    @PostMapping("/createProduct")
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
//        logger.info((Supplier<String>) productDTO.getPrice());
        System.out.println(productDTO.getPrice());
        try {
            productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("A product created");
        } catch (Exception e) {
            throw new RuntimeException("there is a problem while creating a product " + e.getMessage());
        }
    }
    //todo public read;
    //todo public update;
    //todo public delete;
}
