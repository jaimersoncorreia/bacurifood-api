package tech.bacuri.bacurifood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import tech.bacuri.bacurifood.BacurifoodApiApplication;
import tech.bacuri.bacurifood.domain.model.Permissao;
import tech.bacuri.bacurifood.domain.repository.PermissaoRepository;

import java.util.Objects;

public class PermissaoMainTest {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BacurifoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        PermissaoRepository permissaoRepository = context.getBean(PermissaoRepository.class);

        Permissao permissao0 = Permissao.builder().nome("Permissao0").descricao("Permissao Descrição0").build();
        Permissao permissao1 = Permissao.builder().nome("Permissao1").descricao("Permissao Descrição1").build();
        assert permissaoRepository.salvar(permissao0) != null : "Era para ter vindo permisao";
        assert permissaoRepository.salvar(permissao1) != null : "Era para ter vindo permisao";

        assert Objects.equals(permissaoRepository.obter(1L).getNome(), "Permissao0") : "Não é Permissao0";
        assert Objects.equals(permissaoRepository.obter(2L).getNome(), "Permissao1") : "Não é Permissao1";

        assert permissaoRepository.todas().size() == 2 : "Era para tem vindo 2";

        permissaoRepository.remover(permissaoRepository.obter(1L));

        assert permissaoRepository.obter(1L) == null : "Era vim null";
    }
}
