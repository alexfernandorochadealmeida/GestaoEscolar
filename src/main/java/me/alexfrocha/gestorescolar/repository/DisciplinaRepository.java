package me.alexfrocha.gestorescolar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.alexfrocha.gestorescolar.model.Disciplina;
import me.alexfrocha.gestorescolar.model.User;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>{
    List<Disciplina> findByProfessor(User professor);

}
