package tech.bacuri.bacurifood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import tech.bacuri.bacurifood.BacurifoodApiApplication;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.repository.CozinhaRepository;

public class AtualizarCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BacurifoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);


        CozinhaRepository teste = context.getBean(CozinhaRepository.class);

        Cozinha brasileira = Cozinha.builder().id(1L).nome("Brasileira").build();
        teste.save(brasileira);


        System.out.println("teste.obter(1L) = " + teste.getReferenceById(1L));
    }
}
