package me.alexfrocha.gestorescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import me.alexfrocha.gestorescolar.model.Disciplina;
import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.DisciplinaRepository;
import me.alexfrocha.gestorescolar.repository.UserRepository;
import me.alexfrocha.gestorescolar.utils.CargoEnum;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping
    public String paginaInicial(Model model) {
        User usuario = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        model.addAttribute("usuario", usuario);
        model.addAttribute("verificandoSeEAluno", usuario.verificandoPermissao(CargoEnum.ALUNO));
        model.addAttribute("disciplinas", disciplinas);
        if(usuario.getRole().equals("ADMIN")) return "redirect:/admin/usuarios";
        return "dashboard";
    }


}
