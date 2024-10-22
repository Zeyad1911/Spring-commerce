package sim.ecommerce.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncrypted {

    @Bean
    public PasswordEncoder passwordEncoderService() {
        return new BCryptPasswordEncoder();
    }
}
