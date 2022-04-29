package me.alexfrocha.gestorescolar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import me.alexfrocha.gestorescolar.dto.AdminUsuariosDTO;
import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.UserRepository;
import me.alexfrocha.gestorescolar.utils.CargoEnum;
import me.alexfrocha.gestorescolar.utils.RouteManager;

@Controller
@RequestMapping("/admin")
public class AdminUsuariosController extends RouteManager {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/usuarios")
    public String home(Model model, @RequestParam(value = "pagina", required = false) Integer pagina, @RequestParam(value = "email", required = false) String email) {
        if(!super.verificar(userRepository, CargoEnum.ADMIN)) return "redirect:/";
        int paginaVerificada = 0;
        if(pagina != null) paginaVerificada = pagina;
        
        Pageable pageable = PageRequest.of(paginaVerificada, 15, Sort.by("nome").descending());
        Page<User> usuarios = userRepository.findAll(pageable);;
        List<Integer> paginas = new ArrayList<>();
        for(int i = 1; i <= usuarios.getTotalPages(); i++) paginas.add(i);
        List<Boolean> booleanos = new ArrayList<>();
        booleanos.add(true);
        booleanos.add(false);

        model.addAttribute("paginaAtual", usuarios.getNumber());
        model.addAttribute("paginasTotal", usuarios.getTotalPages());
        model.addAttribute("paginas", paginas);
        model.addAttribute("usuariosEncontrados", usuarios.getTotalElements());
        model.addAttribute("usuariosPage", usuarios);
        model.addAttribute("paginaVerificada", paginaVerificada);
        model.addAttribute("Cargos", CargoEnum.values());
        if(email != null) model.addAttribute("usuarios", userRepository.findByEmail(email));
        if(email == null) model.addAttribute("usuarios", usuarios.toList());
        model.addAttribute("booleanos", booleanos);
        return "usuarios/home";
    } 

    @PostMapping("/usuarios/salvar")
    public String salvarAlteracoes(AdminUsuariosDTO adminUsuariosDTO) {
        adminUsuariosDTO.salvarAlteracoes(userRepository);
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/procurando")
    public String procurandoEmail(@RequestParam("email") String email) {
        return "redirect:/admin/usuarios?email=" + email;
    }

}
