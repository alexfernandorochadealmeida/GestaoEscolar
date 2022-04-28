package me.alexfrocha.gestorescolar.dto;

import me.alexfrocha.gestorescolar.model.Disciplina;
import me.alexfrocha.gestorescolar.model.User;
public class DisciplinaDTO {
    private String nomeDaDisciplina;
    private String nomeDoProfessor;

    public String getNomeDaDisciplina() {
        return this.nomeDaDisciplina;
    }

    public void setNomeDaDisciplina(String nomeDaDisciplina) {
        this.nomeDaDisciplina = nomeDaDisciplina;
    }

    public String getNomeDoProfessor() {
        return this.nomeDoProfessor;
    }

    public void setNomeDoProfessor(String nomeDoProfessor) {
        this.nomeDoProfessor = nomeDoProfessor;
    }

    public Disciplina toDisciplina(User professor) {
        if(nomeDaDisciplina.isEmpty()) throw new IllegalArgumentException("Informe um nome para a disciplina");
        if(nomeDoProfessor.isEmpty()) throw new IllegalArgumentException("Selecione um responsável para a disciplina");
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(nomeDaDisciplina);
        disciplina.setProfessor(professor);
        return disciplina;
    }

    
    public Disciplina editarDisciplina(User professor, Long id) {
        if(nomeDaDisciplina.isEmpty()) throw new IllegalArgumentException("Informe um nome para a disciplina");
        if(nomeDoProfessor.isEmpty()) throw new IllegalArgumentException("Selecione um responsável para a disciplina");
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(nomeDaDisciplina);
        disciplina.setProfessor(professor);
        disciplina.setId(id);
        return disciplina;
    }
}
