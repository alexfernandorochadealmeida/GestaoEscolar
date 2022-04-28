package me.alexfrocha.gestorescolar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.alexfrocha.gestorescolar.dto.AtividadeDTO;
import me.alexfrocha.gestorescolar.model.Atividade;
import me.alexfrocha.gestorescolar.model.Disciplina;
import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.AtividadeRepository;
import me.alexfrocha.gestorescolar.repository.DisciplinaRepository;
import me.alexfrocha.gestorescolar.repository.UserRepository;
import me.alexfrocha.gestorescolar.utils.CargoEnum;
import me.alexfrocha.gestorescolar.utils.RouteManager;

@Controller
@RequestMapping("/professor")
public class AdminAtividadeController extends RouteManager {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping("atividades")
    private String homeAtividade(Model model) {
        if(!super.verificar(userRepository, CargoEnum.PROFESSOR)) return "redirect:/";

        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User professor = userRepository.findByEmail(email);

        List<Disciplina> disciplinas = disciplinaRepository.findByProfessor(professor);
        List<Atividade> atividades = atividadeRepository.findByProfessor(professor);
        model.addAttribute("atividades", atividades);
        model.addAttribute("disciplinas", disciplinas);
        return "atividade/criar";
    }

    @PostMapping("atividade/criar")
    private String criandoAtividade(AtividadeDTO atividadeDTO, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User professor = userRepository.findByEmail(email);

        // Enviar erro a pagina principal
        List<Disciplina> disciplinas = disciplinaRepository.findByProfessor(professor);
        List<Atividade> atividades = atividadeRepository.findByProfessor(professor);
        try {
            Atividade novaAtividade = atividadeDTO.toAtividade(disciplinaRepository);
            novaAtividade.setProfessor(professor);
            atividadeRepository.save(novaAtividade);
            return "redirect:/professor/atividades";
        } catch (IllegalArgumentException e) {
            model.addAttribute("atividades", atividades);
            model.addAttribute("disciplinas", disciplinas);
            model.addAttribute("mensagem_de_erro", e.getMessage());
            return "atividade/criar";
        }
    }

    @GetMapping("atividade/deletar/{id}")
    private String deletarAtividade(@PathVariable("id") Long id) {
        Optional<Atividade> atividadeOuNulo = atividadeRepository.findById(id);
        if(atividadeOuNulo.isPresent()) atividadeRepository.delete(atividadeOuNulo.get());
        return "redirect:/professor/atividades";
    }
    
    @GetMapping("atividade/editar/{id}")
    private String formularioParaEditarAtividade(@PathVariable("id") Long id, Model model) {
        Optional<Atividade> atividadeOuNulo = atividadeRepository.findById(id);
        if(atividadeOuNulo.isPresent()) model.addAttribute("objetoAtividade", atividadeOuNulo.get());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User professor = userRepository.findByEmail(email);
        List<Disciplina> disciplinas = disciplinaRepository.findByProfessor(professor);
        List<Atividade> atividades = atividadeRepository.findByProfessor(professor);
        model.addAttribute("atividades", atividades);
        model.addAttribute("disciplinas", disciplinas);
        return "atividade/editar";
    }

    @PostMapping("atividade/editar")
    private String editarAtividade(AtividadeDTO atividadeDTO) {
        Atividade atividadeEditada = atividadeDTO.editarAtividade(disciplinaRepository, userRepository);
        atividadeRepository.save(atividadeEditada);
        return "redirect:/professor/atividades";
    }

}
