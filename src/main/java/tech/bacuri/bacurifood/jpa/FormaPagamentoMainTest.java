package tech.bacuri.bacurifood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import tech.bacuri.bacurifood.BacurifoodApiApplication;
import tech.bacuri.bacurifood.domain.model.FormaPagamento;
import tech.bacuri.bacurifood.domain.repository.FormaPagamentoRepository;

import java.util.Objects;

public class FormaPagamentoMainTest {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BacurifoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        FormaPagamentoRepository formaPagamentoRepository = context.getBean(FormaPagamentoRepository.class);

        FormaPagamento formaPagamento0 = FormaPagamento.builder().descricao("FormaPagamento Descrição0").build();
        FormaPagamento formaPagamento1 = FormaPagamento.builder().descricao("FormaPagamento Descrição1").build();
        assert formaPagamentoRepository.salvar(formaPagamento0) != null : "Era para ter vindo permisao";
        assert formaPagamentoRepository.salvar(formaPagamento1) != null : "Era para ter vindo permisao";

        assert Objects.equals(formaPagamentoRepository.obter(1L).getDescricao(), "FormaPagamento Descrição0") : "Não é FormaPagamento0";
        assert Objects.equals(formaPagamentoRepository.obter(2L).getDescricao(), "FormaPagamento Descrição1") : "Não é FormaPagamento1";

        assert formaPagamentoRepository.todas().size() == 2 : "Era para tem vindo 2";

        formaPagamentoRepository.remover(formaPagamentoRepository.obter(1L));

        assert formaPagamentoRepository.obter(1L) == null : "Era vim null";
    }
}
