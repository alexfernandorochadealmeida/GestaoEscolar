package me.alexfrocha.gestorescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.UserRepository;

@Controller
public class TurmaController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/turma")
    public String home(Model model) {
        List<User> usuarios = userRepository.findAll(); 
        model.addAttribute("usuarios", usuarios);
        return "turma/home";
    }
}
