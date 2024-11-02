package org.example.up_itog_10_2024.Controllers;


import org.example.up_itog_10_2024.Models.Role;
import org.example.up_itog_10_2024.Models.User;
import org.example.up_itog_10_2024.Repositories.RoleRepository;
import org.example.up_itog_10_2024.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Optional;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registrationForm() {
        return "registration";
    }
    @GetMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Model model) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "registration";
        }
        Optional<Role> role = roleRepository.findById((long)1);
        user.setActive(true);
        user.setName("Guest");
        user.setRole(role.orElse(null));
        user.setStatus(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }



}
