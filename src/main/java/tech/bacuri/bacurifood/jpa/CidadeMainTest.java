package tech.bacuri.bacurifood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import tech.bacuri.bacurifood.BacurifoodApiApplication;
import tech.bacuri.bacurifood.domain.model.Cidade;
import tech.bacuri.bacurifood.domain.model.Estado;
import tech.bacuri.bacurifood.domain.repository.CidadeRepository;

import java.util.Objects;

public class CidadeMainTest {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BacurifoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CidadeRepository cidadeRepository = context.getBean(CidadeRepository.class);

        Cidade manaus = Cidade.builder().nome("Manaus").estado(Estado.builder().id(1L).nome("Amazonas").build()).build();
        Cidade careiroDaVarzea = Cidade.builder().nome("Careiro da Várzea").estado(Estado.builder().id(1L).nome("Amazonas").build()).build();
        cidadeRepository.save(manaus);
        cidadeRepository.save(careiroDaVarzea);

        assert Objects.equals(cidadeRepository.findById(1L).get().getNome(), "Manaus") : "Não é Manaus";
        assert Objects.equals(cidadeRepository.findById(2L).get().getNome(), "Careiro da Várzea") : "Não é Careiro da Várzea";

        assert cidadeRepository.findAll().size() == 2 : "Era para tem vindo 2";

        cidadeRepository.delete(cidadeRepository.findById(1L).get());

        assert cidadeRepository.findById(1L).isPresent() : "Era vim null";
    }
}
