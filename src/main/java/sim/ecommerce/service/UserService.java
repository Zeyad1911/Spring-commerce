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
import sim.ecommerce.model.Role;
import sim.ecommerce.model.User;
import sim.ecommerce.repository.UserRepository;
import sim.ecommerce.utils.PasswordEncrypted;

import java.awt.geom.RectangularShape;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncrypted encrypted;

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User can not be found")
        );
    }

    @Autowired
    UserService(UserRepository userRepository, PasswordEncrypted encrypted) {
        this.userRepository = userRepository;
        this.encrypted = encrypted;
    }

    public void save(RegisterDTO registerDTO) {
        var coder = encrypted.passwordEncoderService();
        Optional<User> userByEmail = userRepository.findUserByEmail(registerDTO.getEmail());

            if (userByEmail.isEmpty()) {
                User user = new User();
                user.setUsername(registerDTO.getUsername());
                user.setEmail(registerDTO.getEmail());
                user.setHashed_password(coder.encode(registerDTO.getPassword()));
                user.setRole(Role.USER);
                userRepository.save(user);
            }
            else {
                throw new UserExistException("Email is used by another account");
            }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found") );
        return new UserInfo(user);
    }
}
