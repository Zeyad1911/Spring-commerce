package sim.ecommerce.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sim.ecommerce.DTOs.CartDTO;
import sim.ecommerce.model.Cart;
import sim.ecommerce.service.CartService;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("{id}")
    public CartDTO createCart(@PathVariable long id) {
        return cartService.createCartForUser(id);
    }
}
