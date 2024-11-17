package sim.ecommerce.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("add/{id}")
    public CartDTO addToCart(@PathVariable long id, @RequestParam long product_id) {
        try {
            System.out.println("add endpoint: " + cartService.addItemsToCart(id, product_id));
            return cartService.addItemsToCart(id, product_id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
