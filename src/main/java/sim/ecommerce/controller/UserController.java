package sim.ecommerce.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sim.ecommerce.DTOs.LoginDTO;
import sim.ecommerce.DTOs.LoginResponseDTO;
import sim.ecommerce.DTOs.RegisterDTO;
import sim.ecommerce.SecurityInfo.UserInfo;
import sim.ecommerce.service.JwtService;
import sim.ecommerce.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("**")
@Validated
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    @Autowired
    UserController(UserService userService,
                   AuthenticationManager authenticationManager, JwtService jwtService){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO login) {
        try {
            UsernamePasswordAuthenticationToken token
                                                        = new UsernamePasswordAuthenticationToken
                    (login.getEmail(), login.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            System.out.println(token.getCredentials());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserInfo user = (UserInfo) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(user.getUsername());
            System.out.println(jwtToken);
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO("user has been logged in"
            , jwtToken);
            return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);

        } catch (UsernameNotFoundException exception) {
            LoginResponseDTO loginResponseDTO =
                    new LoginResponseDTO("User not found",null);
            System.out.println(exception.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginResponseDTO );
        }
    }

}
