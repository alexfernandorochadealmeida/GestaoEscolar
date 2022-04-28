package me.alexfrocha.gestorescolar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import me.alexfrocha.gestorescolar.model.Atividade;
import me.alexfrocha.gestorescolar.model.Disciplina;
import me.alexfrocha.gestorescolar.repository.AtividadeRepository;
import me.alexfrocha.gestorescolar.repository.DisciplinaRepository;

@Controller
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @GetMapping("disciplina/{id}")
    public String home(@PathVariable("id") Long id, Model model) {
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);

        List<Atividade> atividades = atividadeRepository.findByDisciplina(disciplina.get());
        model.addAttribute("atividades", atividades);
        model.addAttribute("disciplina", disciplina.get());
        return "disciplina/home";
    }
    
}
