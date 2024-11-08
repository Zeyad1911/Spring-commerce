package sim.ecommerce.mappers;

import org.springframework.stereotype.Component;
import sim.ecommerce.DTOs.ProductDTO;
import sim.ecommerce.model.Product;

@Component
public class ProductDTOMapper {

    public ProductDTO productDTOMap(Product product) {
        ProductDTO p = new ProductDTO();
        p.setDescription(product.getDescription());
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setStockQuantity(product.getStockQuantity());
        p.setImageUrl(product.getImageUrl());
        return p;
    }
}
