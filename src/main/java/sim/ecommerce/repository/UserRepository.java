package sim.ecommerce.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sim.ecommerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("""
    select u from User u where u.email = :email
    """)
    User findUserByEmail(@Param("email") String email);

    User findUserByUsername(@Param("username") String username);
}
