package sim.ecommerce.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginDTO {
    @Email
    private String email;
    @Size(min = 8, message = "Password should be at least a 8 long characters")
    private String password;
}
