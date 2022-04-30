package me.alexfrocha.gestorescolar.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import me.alexfrocha.gestorescolar.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
     User findByEmail(String username);
     List<User> findByRole(String role);
}
