package me.alexfrocha.gestorescolar.dto;

import java.util.Optional;

import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.UserRepository;

public class AdminUsuariosDTO {
    private String idUsuario;
    private String role;
    private String enabled;


    public String getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }


    public void salvarAlteracoes(UserRepository userRepository) {
        Optional<User> userOptional = userRepository.findById(Long.parseLong(idUsuario));
        User user = userOptional.get();
        user.setId(Long.parseLong(idUsuario));
        user.setRole(role);
        user.setEnabled(Boolean.parseBoolean(enabled));
        userRepository.save(user);
    }

}
