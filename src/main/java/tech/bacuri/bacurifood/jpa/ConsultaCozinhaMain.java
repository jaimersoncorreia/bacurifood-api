package tech.bacuri.bacurifood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import tech.bacuri.bacurifood.BacurifoodApiApplication;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BacurifoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);


        CozinhaRepository cadastroCozinha = context.getBean(CozinhaRepository.class);

        cadastroCozinha.save(Cozinha.builder().nome("Japonesa").build());
        cadastroCozinha.save(Cozinha.builder().nome("Alemã").build());
        cadastroCozinha.save(Cozinha.builder().nome("Francesa").build());

        cadastroCozinha.findAll().forEach(cozinha -> System.out.println("cozinha = " + cozinha));
    }
}
