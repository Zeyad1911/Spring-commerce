package sim.ecommerce.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import sim.ecommerce.model.Product;

import java.util.List;

@Getter
@Setter
public class CartDTO {
    @NotNull
    private long id;
    private long user_id;
    private List<Product> items;
}
