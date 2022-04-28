package me.alexfrocha.gestorescolar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.alexfrocha.gestorescolar.model.Atividade;
import me.alexfrocha.gestorescolar.model.Disciplina;
import me.alexfrocha.gestorescolar.model.User;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    List<Atividade> findByProfessor(User professor);
    List<Atividade> findByDisciplina(Disciplina disciplina);
}
