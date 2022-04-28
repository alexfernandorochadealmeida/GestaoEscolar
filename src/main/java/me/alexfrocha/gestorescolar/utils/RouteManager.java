package me.alexfrocha.gestorescolar.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import me.alexfrocha.gestorescolar.model.User;
import me.alexfrocha.gestorescolar.repository.UserRepository;

public class RouteManager {
    

    public boolean verificar(UserRepository userRepository, CargoEnum cargo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email);
        if(user.verificandoPermissao(CargoEnum.ADMIN)) return true;
        return user.verificandoPermissao(cargo);
    }

}
