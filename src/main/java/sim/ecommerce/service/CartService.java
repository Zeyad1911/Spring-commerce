package sim.ecommerce.service;

import lombok.AllArgsConstructor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sim.ecommerce.DTOs.CartDTO;
import sim.ecommerce.mappers.CartMapper;
import sim.ecommerce.model.Cart;
import sim.ecommerce.model.Product;
import sim.ecommerce.model.User;
import sim.ecommerce.repository.CartRepository;
import sim.ecommerce.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CartMapper cartMapper;
    private final ProductService productService;

    public Cart getCartByID(long id) {
        return cartRepository.findById(id).orElseThrow(
                () -> new RuntimeException("There is no cart with the id")
        );
    }

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


    public CartDTO addItemsToCart(long cart_id, long product_id) {
        Cart cart = getCartByID(cart_id);
        Product product = productService.getProduct(product_id);
        List<Product> cartItems =  cart.getItems();
        cartItems.add(product);
        cart.setItems(cartItems);
        cart.updateTotalPrice();

        return cartMapper.CartMapperDTO(cart);
    }

    public void calculateTotalPrice(long cart_id) {
        Cart cart = getCartByID(cart_id);
        List<Product> cartItems =  cart.getItems();
        cart.updateTotalPrice();
        System.out.println(cart.getTotalPrice());

        //test to be deleted
        System.out.println("cart items + "+cartItems);
    }
}
