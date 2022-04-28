package me.alexfrocha.gestorescolar.dto;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import me.alexfrocha.gestorescolar.model.Atividade;
import me.alexfrocha.gestorescolar.model.Disciplina;
import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.DisciplinaRepository;
import me.alexfrocha.gestorescolar.repository.UserRepository;

public class AtividadeDTO {
    private String id;
    private String titulo;
    private String descricao;
    private String link;
    private String pontuacao;
    private String disciplina;



    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPontuacao() {
        return this.pontuacao;
    }

    public void setPontuacao(String pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getDisciplina() {
        return this.disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }


    public Atividade toAtividade(DisciplinaRepository disciplinaRepository) {

        if(titulo.isBlank()) throw new IllegalArgumentException("O campo título está vazio");
        if(link.isBlank()) throw new IllegalArgumentException("O campo link está vazio");
        if(pontuacao.isBlank()) throw new IllegalArgumentException("O campo pontuação está vazio");
        if(!link.startsWith("https://")) throw new IllegalArgumentException("Link inválido (Exemplo: https://site.com)");
        if(disciplina.isBlank()) throw new IllegalArgumentException("É necessário que escolha uma disciplina");

        Long idDaDisciplina = Long.parseLong(disciplina);

        Optional<Disciplina> disciplina = disciplinaRepository.findById(idDaDisciplina);
        Atividade atividade = new Atividade();
        
        atividade.setNome(titulo);

        if(!descricao.isBlank()) atividade.setDescricao(descricao);
        
        atividade.setUrl(link);
        atividade.setDisciplina(disciplina.get());
        atividade.setPontuacao(pontuacao);
        return atividade;
    }

    
    public Atividade editarAtividade(DisciplinaRepository disciplinaRepository, UserRepository userRepository) {

        
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User professor = userRepository.findByEmail(email);

        if(titulo.isBlank()) throw new IllegalArgumentException("O campo título está vazio");
        if(link.isBlank()) throw new IllegalArgumentException("O campo link está vazio");
        if(pontuacao.isBlank()) throw new IllegalArgumentException("O campo pontuação está vazio");
        if(!link.startsWith("https://")) throw new IllegalArgumentException("Link inválido (Exemplo: https://site.com)");
        if(disciplina.isBlank()) throw new IllegalArgumentException("É necessário que escolha uma disciplina");

        Long idDaDisciplina = Long.parseLong(disciplina);

        Optional<Disciplina> disciplina = disciplinaRepository.findById(idDaDisciplina);
        Atividade atividade = new Atividade();
        
        Long idFormatado = Long.parseLong(id);
        atividade.setId(idFormatado);
        atividade.setNome(titulo);

        if(!descricao.isBlank()) atividade.setDescricao(descricao);
        atividade.setUrl(link);
        atividade.setDisciplina(disciplina.get());
        atividade.setPontuacao(pontuacao.replace(" pontos", "").replace(" ponto", ""));
        atividade.setProfessor(professor);
        return atividade;
    }
}
