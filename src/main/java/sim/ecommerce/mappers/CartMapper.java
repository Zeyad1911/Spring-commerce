package sim.ecommerce.mappers;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import sim.ecommerce.DTOs.CartDTO;
import sim.ecommerce.model.Cart;

@Component
@Lazy
public class CartMapper {

    public CartDTO CartMapperDTO(@NotNull Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUser_id(cart.getUser().getId());
        cartDTO.setItems(cart.getItems());
        return cartDTO;
    }
}
