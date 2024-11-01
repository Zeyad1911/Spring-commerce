package sim.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sim.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
