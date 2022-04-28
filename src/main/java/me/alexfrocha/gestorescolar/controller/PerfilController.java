package me.alexfrocha.gestorescolar.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.UserRepository;

@Controller
public class PerfilController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("perfil/{id}")
    private String home(@PathVariable("id") Long id, Model model) {
        Optional<User> usuario = userRepository.findById(id);
        model.addAttribute("usuario", usuario.get());
        return "perfil/home";
    }
}
