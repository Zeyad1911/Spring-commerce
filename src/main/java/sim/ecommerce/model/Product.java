package sim.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private boolean isAvailable;
    private String imageUrl;
}
