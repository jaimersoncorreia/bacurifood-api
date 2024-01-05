package tech.bacuri.bacurifood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import tech.bacuri.bacurifood.BacurifoodApiApplication;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.repository.RestauranteRepository;

import java.math.BigDecimal;

public class IncluirRestauranteMain {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BacurifoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);


        RestauranteRepository repository = context.getBean(RestauranteRepository.class);

        Restaurante picanharia = repository.salvar(Restaurante.builder().nome("Picanharia").taxaFrete(new BigDecimal("50.00")).build());
        Restaurante maminha = repository.salvar(Restaurante.builder().nome("Maminha").taxaFrete(new BigDecimal("50.00")).build());
        Restaurante file = repository.salvar(Restaurante.builder().nome("Filé").taxaFrete(new BigDecimal("50.00")).build());

        System.out.println("picanharia = " + picanharia);
        System.out.println("maminha = " + maminha);
        System.out.println("file = " + file);

        repository.todas().forEach(System.out::println);
    }
}
