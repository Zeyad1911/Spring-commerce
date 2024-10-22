package sim.ecommerce.service;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sim.ecommerce.DTOs.RegisterDTO;
import sim.ecommerce.SecurityInfo.UserInfo;
import sim.ecommerce.exceptions.UserExistException;
import sim.ecommerce.model.User;
import sim.ecommerce.repository.UserRepository;
import sim.ecommerce.utils.PasswordEncrypted;

import java.awt.geom.RectangularShape;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncrypted encrypted;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncrypted encrypted) {
        this.userRepository = userRepository;
        this.encrypted = encrypted;
    }

    public User save(RegisterDTO registerDTO) {
        var coder = encrypted.passwordEncoderService();

            if (userRepository.findUserByEmail(registerDTO.getEmail()) == null) {
                User user = new User();
                user.setUsername(registerDTO.getUsername());
                user.setEmail(registerDTO.getEmail());
                user.setHashed_password(coder.encode(registerDTO.getPassword()));
                userRepository.save(user);
                return user;
            }
            else {
                throw new UserExistException("user already exist");
            }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        return new UserInfo(user);
    }
}
