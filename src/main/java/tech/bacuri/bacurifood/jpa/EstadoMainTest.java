package tech.bacuri.bacurifood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import tech.bacuri.bacurifood.BacurifoodApiApplication;
import tech.bacuri.bacurifood.domain.model.Estado;
import tech.bacuri.bacurifood.domain.repository.EstadoRepository;

import java.util.Objects;

public class EstadoMainTest {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BacurifoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        EstadoRepository estadoRepository = context.getBean(EstadoRepository.class);

        Estado acre = Estado.builder().nome("Acre").build();
        Estado roraima = Estado.builder().nome("Roraima").build();
        estadoRepository.save(acre);
        estadoRepository.save(roraima);

        assert Objects.equals(estadoRepository.findById(1L).get().getNome(), "Acre") : "Não é Acre";
        assert Objects.equals(estadoRepository.findById(2L).get().getNome(), "Roraima") : "Não é Roraima";

        assert estadoRepository.findAll().size() == 2 : "Era para tem vindo 2";

        estadoRepository.delete(estadoRepository.findById(1L).get());

        assert estadoRepository.findById(1L).isPresent() : "Era vim null";
    }
}
