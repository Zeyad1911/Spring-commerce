package sim.ecommerce.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sim.ecommerce.DTOs.LoginDTO;
import sim.ecommerce.DTOs.RegisterDTO;
import sim.ecommerce.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("**")
@Validated
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    UserController(UserService userService, AuthenticationManager authenticationManager){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String login() {
        return "this is login";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "this register";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO registerDTO) {
        userService.save(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered now");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO login) {
        try {
            UsernamePasswordAuthenticationToken token
                                                        = new UsernamePasswordAuthenticationToken
                    (login.getEmail(), login.getPassword());

            Authentication authentication = authenticationManager.authenticate(token);
            System.out.println(authentication.isAuthenticated());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            authentication.getCredentials();
            return ResponseEntity.status(HttpStatus.OK).body("user is logged in");

        } catch (UsernameNotFoundException exception) {
            System.out.println(exception.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

}
