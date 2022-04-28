package me.alexfrocha.gestorescolar;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        mostrarDiretorio("fotos-usuario", registry);
    }

    private void mostrarDiretorio(String nomeDoDiretorio, ResourceHandlerRegistry registry) {
        Path diretorio = Paths.get(nomeDoDiretorio);
        String caminho = diretorio.toFile().getAbsolutePath();
        if(nomeDoDiretorio.startsWith("../")) nomeDoDiretorio = nomeDoDiretorio.replace("../", "");
        registry.addResourceHandler("/" + nomeDoDiretorio + "/**").addResourceLocations("file:/" + caminho + "/");
    }

}
