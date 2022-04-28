package me.alexfrocha.gestorescolar.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String descricao;
    private String pontuacao;
    private String url;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User professor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Disciplina disciplina;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPontuacao() {
        return this.pontuacao;
    }

    public void setPontuacao(String pontuacao) {
        float valor = Float.parseFloat(pontuacao);
        String palavra = " pontos";
        if(valor == 1) {
            palavra = " ponto";
        }
        String fraseFormatada = valor + palavra;
        this.pontuacao = fraseFormatada;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getProfessor() {
        return this.professor;
    }

    public void setProfessor(User professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return this.disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }


    
}
