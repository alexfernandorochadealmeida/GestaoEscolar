package me.alexfrocha.gestorescolar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.alexfrocha.gestorescolar.dto.DisciplinaDTO;
import me.alexfrocha.gestorescolar.model.Disciplina;
import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.DisciplinaRepository;
import me.alexfrocha.gestorescolar.repository.UserRepository;
import me.alexfrocha.gestorescolar.utils.CargoEnum;
import me.alexfrocha.gestorescolar.utils.RouteManager;

@Controller
@RequestMapping("/admin")
public class AdminDisciplinaController extends RouteManager{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;
    
    
    @GetMapping("/disciplinas")
    public String formulario(Model model) {
        if(!super.verificar(userRepository, CargoEnum.ADMIN)) return "redirect:/";

        List<User> professores = userRepository.findByRole(CargoEnum.PROFESSOR.name());
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        model.addAttribute("professores", professores);
        model.addAttribute("disciplinas", disciplinas);
        return "disciplina/criar";
    }

    @PostMapping("/disciplina/criar")
    public String criarDisciplina(DisciplinaDTO disciplinaDTO, Model model) {
        if(!super.verificar(userRepository, CargoEnum.ADMIN)) return "redirect:/";
        try {
            User professor = userRepository.findByEmail(disciplinaDTO.getNomeDoProfessor());
            Disciplina disciplina = disciplinaDTO.toDisciplina(professor);
            disciplinaRepository.save(disciplina);
            return "redirect:/admin/disciplinas";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "redirect:/admin/disciplinas";
        }
    }

    @GetMapping("/disciplina/deletar/{id}")
    public String deletarDisciplina(@PathVariable("id") Long id) {
        if(!super.verificar(userRepository, CargoEnum.ADMIN)) return "redirect:/";


        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        if(disciplina.isPresent()) disciplinaRepository.deleteById(id);
        return "redirect:/admin/disciplinas";
    }

    
    @GetMapping("/disciplina/editar/{id}")
    public String editarDisciplina(@PathVariable("id") Long id, Model model) {
        if(!super.verificar(userRepository, CargoEnum.ADMIN)) return "redirect:/";


        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        List<User> professores = userRepository.findByRole(CargoEnum.PROFESSOR.name());
        if(disciplina.isPresent()) {
            model.addAttribute("professores", professores);
            model.addAttribute("disciplina", disciplina.get());
            return "disciplina/editar";
        }
        return "redirect:/admin/disciplinas";
    }

    @PostMapping("/disciplina/editar/{id}")
    public String editarDisciplinaPost(DisciplinaDTO disciplinaDTO, @PathVariable("id") Long id, Model model) {
        if(!super.verificar(userRepository, CargoEnum.ADMIN)) return "redirect:/";


        User professor = userRepository.findByEmail(disciplinaDTO.getNomeDoProfessor());
        Disciplina novaDisciplina = disciplinaDTO.editarDisciplina(professor, id);
        disciplinaRepository.save(novaDisciplina);
        return "redirect:/admin/disciplinas";
    }
}
