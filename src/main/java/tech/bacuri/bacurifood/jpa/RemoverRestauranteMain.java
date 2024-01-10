package tech.bacuri.bacurifood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import tech.bacuri.bacurifood.BacurifoodApiApplication;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.repository.RestauranteRepository;

import java.math.BigDecimal;

public class RemoverRestauranteMain {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BacurifoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);


        RestauranteRepository repository = context.getBean(RestauranteRepository.class);

        repository.save(Restaurante.builder().nome("Restaurante da Joana").taxaFrete(new BigDecimal("50.00")).cozinha(Cozinha.builder().id(1L).build()).build());
        repository.delete(Restaurante.builder().id(4L).build());

        assert repository.findById(4L).isPresent();
        System.out.println(repository.findById(4L).isPresent());
        System.out.println("teste.obter(4L) = " + repository.findById(4L));

    }
}
