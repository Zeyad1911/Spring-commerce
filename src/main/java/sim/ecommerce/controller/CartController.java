package sim.ecommerce.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sim.ecommerce.DTOs.CartDTO;
import sim.ecommerce.model.Cart;
import sim.ecommerce.service.CartService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public Cart getCartById(long id) {
        return cartService.getCartByID(id);
    }

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

    @PostMapping("checkout")
    public ResponseEntity<String> checkout(long cart_id) {
                Cart cart = cartService.getCartByID(cart_id);
                try {
                    BigDecimal billCost = cartService.calculateTotalPrice(cart_id);
                    return ResponseEntity.status(HttpStatus.OK).body("Here is your bill");
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("there is a problem with your cart");
                }
    }
}
