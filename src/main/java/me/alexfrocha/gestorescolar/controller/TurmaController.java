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
        List<User> usuarios = (List<User>) userRepository.findByRole("ALUNO"); 
        List<User> professores = (List<User>) userRepository.findByRole("PROFESSOR");
        List<User> admin = (List<User>) userRepository.findByRole("ADMIN");  
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("professores", professores);
        model.addAttribute("admin", admin);
        return "turma/home";
    }
}
