package sim.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sim.ecommerce.model.Cart;
import sim.ecommerce.model.User;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {


    Optional<Cart> findByUser(User user);
}
