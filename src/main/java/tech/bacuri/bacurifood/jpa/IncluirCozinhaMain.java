package tech.bacuri.bacurifood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import tech.bacuri.bacurifood.BacurifoodApiApplication;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.repository.CozinhaRepository;

public class IncluirCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BacurifoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);


        CozinhaRepository teste = context.getBean(CozinhaRepository.class);

        Cozinha japonesa = teste.salvar(Cozinha.builder().nome("Japonesa").build());
        Cozinha alema = teste.salvar(Cozinha.builder().nome("Alemã").build());
        Cozinha francesa = teste.salvar(Cozinha.builder().nome("Francesa").build());

        System.out.println("japonesa = " + japonesa);
        System.out.println("alema = " + alema);
        System.out.println("francesa = " + francesa);

        teste.listar().forEach(System.out::println);
    }
}
