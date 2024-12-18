package sim.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
    private User user;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @Lazy
    private List<Product> items;
    private BigDecimal totalPrice;

    public void updateTotalPrice() {
        this.totalPrice = getItems()
                .stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
