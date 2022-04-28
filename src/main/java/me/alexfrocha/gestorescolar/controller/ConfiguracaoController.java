package me.alexfrocha.gestorescolar.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.UserRepository;
import me.alexfrocha.gestorescolar.utils.FileUploadUtil;

@Controller
public class ConfiguracaoController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/config")
    public String home(Model model) {
        
     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     String email = auth.getName();
     User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);

        return "config/home";
    }

    @PostMapping("/config/salvar")
    public String salvarConfig(HttpServletRequest request, @RequestParam("foto") MultipartFile multipartFile, @RequestParam("nome") String nome, @RequestParam("celular") String celular, @RequestParam("sobremim") String sobremim) throws IOException, ServletException {
        String nomeDoArquivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email2 = auth.getName();
        User user = userRepository.findByEmail(email2);
        if(multipartFile.isEmpty()) {
            user.setNome(nome);
            if(!sobremim.isEmpty()) user.setSobremim(sobremim);
            if(!celular.isEmpty()) user.setCelular(celular);
            userRepository.save(user);
            return "redirect:/";
        }

        user.setNome(nome);
        user.setFoto(nomeDoArquivo.replaceAll(" ", ""));
        User usuarioSalvo = userRepository.save(user);

        String diretorioDasFotos = "fotos-usuario/" + usuarioSalvo.getId();
        FileUploadUtil.salvarArquivo(diretorioDasFotos, nomeDoArquivo, multipartFile);
        return "redirect:/";
    }
}
