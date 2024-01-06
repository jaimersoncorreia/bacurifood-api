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
        estadoRepository.salvar(acre);
        estadoRepository.salvar(roraima);

        assert Objects.equals(estadoRepository.obter(1L).getNome(), "Acre") : "Não é Acre";
        assert Objects.equals(estadoRepository.obter(2L).getNome(), "Roraima") : "Não é Roraima";

        assert estadoRepository.todas().size() == 2 : "Era para tem vindo 2";

        estadoRepository.remover(estadoRepository.obter(1L));

        assert estadoRepository.obter(1L) == null : "Era vim null";
    }
}
