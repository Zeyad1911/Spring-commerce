package sim.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sim.ecommerce.DTOs.ProductDTO;

@RestController
@RequestMapping("/product")
public class ProductController {

    //todo public create;
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {

    }
    //todo public read;
    //todo public update;
    //todo public delete;
}
