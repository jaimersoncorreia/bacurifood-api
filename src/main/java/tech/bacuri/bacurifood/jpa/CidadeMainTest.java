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

        CidadeRepository teste = context.getBean(CidadeRepository.class);

        Cidade manaus = Cidade.builder().nome("Manaus").estado(Estado.builder().id(1L).nome("Amazonas").build()).build();
        Cidade careiroDaVarzea = Cidade.builder().nome("Careiro da Várzea").estado(Estado.builder().id(1L).nome("Amazonas").build()).build();
        teste.salvar(manaus);
        teste.salvar(careiroDaVarzea);

        assert Objects.equals(teste.obter(1L).getNome(), "Manaus") : "Não é Manaus";
        assert Objects.equals(teste.obter(2L).getNome(), "Careiro da Várzea") : "Não é Careiro da Várzea";

        assert teste.listar().size() == 2 : "Era para tem vindo 2";

        teste.remover(teste.obter(1L));

        assert teste.obter(1L) == null : "Era vim null";
    }
}
