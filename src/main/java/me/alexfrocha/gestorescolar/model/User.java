package me.alexfrocha.gestorescolar.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import me.alexfrocha.gestorescolar.utils.CargoEnum;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String celular;
    private String sobremim;
    private boolean enabled;

    @Column(nullable = true, length = 128)
    private String foto;
    private String role;

    public boolean verificandoPermissao(CargoEnum cargo) {
        return this.role.equals(cargo.name());
    }

    @Transient
    public String fotoDePerfil() {
        if(foto == null || id == null) return "/fotos-usuario/default/normal.jpg";
        return "/fotos-usuario/" + id + "/" + foto;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSobremim() {
        return this.sobremim;
    }

    public void setSobremim(String sobremim) {
        this.sobremim = sobremim;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFoto() {
        return this.foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }



}
