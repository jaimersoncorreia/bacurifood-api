package tech.bacuri.bacurifood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import tech.bacuri.bacurifood.BacurifoodApiApplication;
import tech.bacuri.bacurifood.domain.model.Cozinha;

public class AtualizarCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BacurifoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);


        CadastroCozinha teste = context.getBean(CadastroCozinha.class);

        Cozinha brasileira = Cozinha.builder().id(1L).nome("Brasileira").build();
        teste.salvar(brasileira);


        System.out.println("teste.obter(1L) = " + teste.obter(1L));
    }
}
