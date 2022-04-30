package me.alexfrocha.gestorescolar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import me.alexfrocha.gestorescolar.model.Atividade;
import me.alexfrocha.gestorescolar.model.Disciplina;
import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.AtividadeRepository;
import me.alexfrocha.gestorescolar.repository.DisciplinaRepository;
import me.alexfrocha.gestorescolar.repository.UserRepository;

@ControllerAdvice
public class RequisicoesController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @ModelAttribute("usuarioLogado")
    public User user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email);
        return user;
    }

    @ModelAttribute("disciplinas2")
    public List<Disciplina> disciplina() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email);
        List<Disciplina> disciplinas = disciplinaRepository.findByProfessor(user);
        return disciplinas;
    }

    @ModelAttribute("atividades")
    public List<Atividade> atividade() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email);
        List<Atividade> atividades = atividadeRepository.findByProfessor(user);
        return atividades;
    }
}
