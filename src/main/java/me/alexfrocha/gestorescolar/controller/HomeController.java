package me.alexfrocha.gestorescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
    public ModelAndView paginaInicial() {
        ModelAndView mv = new ModelAndView("dashboard");
        User usuario = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        mv.addObject("usuario", usuario);
        mv.addObject("verificandoSeEAluno", usuario.verificandoPermissao(CargoEnum.ALUNO));
        mv.addObject("disciplinas", disciplinas);
        return mv;
    }


}
