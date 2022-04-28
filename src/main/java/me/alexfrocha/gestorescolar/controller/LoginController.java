package me.alexfrocha.gestorescolar.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import me.alexfrocha.gestorescolar.dto.UserDTO;
import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.UserRepository;

@Controller
public class LoginController {
    
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/entrar")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return "redirect:/";
        }

        UserDTO userDto = new UserDTO();
        if(error != null) model.addAttribute("error", "Credenciais inválidas");
        model.addAttribute("user", userDto);
        return "login";
    }

    @PostMapping("/criarConta")
    public String criarConta(@Valid UserDTO userDto, HttpServletRequest request, Model model, BindingResult result) throws ServletException {
        try {

            if(result.hasErrors()) return "login";
            model.addAttribute("user", userDto);
            User user = userDto.toUser();
            User verificandoSeOEmailJaERegistrado = userRepository.findByEmail(userDto.getUsername());

            if(verificandoSeOEmailJaERegistrado != null) {
                model.addAttribute("mensagem_erro", "Já existe uma conta cadastrada neste email");
                return "cadastro";
            }

            userRepository.save(user);
            request.login(userDto.getUsername(), userDto.getPassword());
            return "redirect:/";

        } catch (IllegalArgumentException e) {
            model.addAttribute("mensagem_erro", e.getMessage());
            return "cadastro";
        }
    }

}
