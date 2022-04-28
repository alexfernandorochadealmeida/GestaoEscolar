package me.alexfrocha.gestorescolar.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    
    public static void salvarArquivo(String diretorioDasFotos, String nomeDoArquivo, MultipartFile multipartFile) throws IOException  {
        Path diretorio = Paths.get(diretorioDasFotos);
        if(!Files.exists(diretorio)) Files.createDirectories(diretorio);
        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path diretorioDoArquivo = diretorio.resolve(nomeDoArquivo.replaceAll(" ", ""));
            Files.copy(inputStream, diretorioDoArquivo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Não foi possível salvar a imagem: " + nomeDoArquivo);
        }
    }

}
