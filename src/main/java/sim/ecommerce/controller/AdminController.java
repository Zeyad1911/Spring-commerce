package sim.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sim.ecommerce.DTOs.RegisterDTO;
import sim.ecommerce.model.Role;
import sim.ecommerce.service.UserService;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/registerAdmin")
    public void registerAdmin(@RequestBody @Valid RegisterDTO registerDTO) {
        userService.save(registerDTO, Role.ADMIN);
    }
}
