package sim.ecommerce.service;

import lombok.AllArgsConstructor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sim.ecommerce.DTOs.CartDTO;
import sim.ecommerce.mappers.CartMapper;
import sim.ecommerce.model.Cart;
import sim.ecommerce.model.User;
import sim.ecommerce.repository.CartRepository;
import sim.ecommerce.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CartMapper cartMapper;

    public CartDTO createCartForUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User can not be found")
        );
        Cart cart = new Cart();
        Optional<Cart> existingCart = cartRepository.findByUser(user);

        if (existingCart.isPresent()) {
            throw new RuntimeException("User already has a cart");
        }
        cart.setUser(user);
        cartRepository.save(cart);
        user.setCart(cart);
        return cartMapper.CartMapperDTO(cart);
    }

    public CartDTO getUserCart(long user_id) {
        try {
            User user = userRepository.findById(user_id)
                    .orElseThrow( () -> new UsernameNotFoundException("User not found"));

            Cart cart = cartRepository.findByUser(user)
                    .orElseThrow( ()-> new RuntimeException("User with the id " + user_id +" do not have any cart") );
            CartDTO cartDTO = cartMapper.CartMapperDTO(cart);
            if (cartDTO == null) {
                throw new IllegalStateException("Failed to map Cart to CartDTO");
            }
            return cartDTO;
        } catch (JpaSystemException exception) {
            throw new JpaSystemException(exception);
        }
    }

    public String deleteCartForUser(long user_id) {
        User user = userService.getUserById(user_id);
        Cart cart = cartRepository.findByUser(user).orElseThrow(
                () -> new RuntimeException("User with the id do not have a cart")
        );
        try {
            cartRepository.delete(cart);
            return "cart has been deleted";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
