package sim.ecommerce.DTOs;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    @NotBlank(message = "Email should not be blank")
    @Email
    private String email;
    @NotBlank(message = "Username should not be blank")

    private String username;
    @NotBlank(message = "Password should not be blank")
    @Size(min = 8, message = "password should be more than 8 letters")
    private String password;
}
