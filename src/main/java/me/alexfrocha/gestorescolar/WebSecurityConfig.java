package me.alexfrocha.gestorescolar;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/css/**", "/img/**", "/js/**", "/criarConta").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin(form -> form
                .loginPage("/entrar")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout.logoutUrl("/sair"))
            .csrf().disable();
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String authorityQuery = "SELECT email,role FROM users WHERE email=?";
        String userQuery = "SELECT email,senha,enabled FROM users WHERE email=?";

        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery(userQuery)
            .authoritiesByUsernameQuery(authorityQuery)
            .passwordEncoder(encoder);
            // .withUser(user)
        
    }

}
