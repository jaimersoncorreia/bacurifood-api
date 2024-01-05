package tech.bacuri.bacurifood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import tech.bacuri.bacurifood.BacurifoodApiApplication;
import tech.bacuri.bacurifood.domain.model.Cozinha;

public class ConsultaCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BacurifoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);


        CadastroCozinha cadastroCozinha = context.getBean(CadastroCozinha.class);
//        CadastroCozinha teste = context.getBean(CadastroCozinha.class);

        cadastroCozinha.salvar(Cozinha.builder().nome("Japonesa").build());
        cadastroCozinha.salvar(Cozinha.builder().nome("Alemã").build());
        cadastroCozinha.salvar(Cozinha.builder().nome("Francesa").build());

        cadastroCozinha.listar().forEach(cozinha -> System.out.println("cozinha = " + cozinha));
    }
}
