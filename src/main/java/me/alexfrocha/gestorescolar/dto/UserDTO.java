package me.alexfrocha.gestorescolar.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import me.alexfrocha.gestorescolar.model.User;

public class UserDTO {


    private String nomeCompleto;
    private String username;
    private String password;
    private String confirmarSenha;

    public String getNomeCompleto() {
        return this.nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmarSenha() {
        return this.confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }


    public User toUser() {
        if(nomeCompleto.isEmpty()) throw new IllegalArgumentException("Você precisa informar o seu nome");
        if(username.isEmpty()) throw new IllegalArgumentException("Você precisa informar o seu email");
        if(password.length() < 6) throw new IllegalArgumentException("A senha precisa ter ao menos 6 caracteres");
        if(!password.equals(confirmarSenha)) throw new IllegalArgumentException("As senhas não são iguais");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        String novaSenha = encoder.encode(password);
        user.setNome(nomeCompleto);
        user.setEmail(username);
        user.setSenha (novaSenha);
        user.setEnabled(true);
        user.setRole("ALUNO");
        return user;
    }
}
