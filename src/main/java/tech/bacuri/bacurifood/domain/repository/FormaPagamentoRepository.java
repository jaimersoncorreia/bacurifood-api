package tech.bacuri.bacurifood.domain.repository;

import tech.bacuri.bacurifood.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> todas();

    FormaPagamento obter(Long id);

    FormaPagamento salvar(FormaPagamento formaPagamento);

    void remover(FormaPagamento formaPagamento);
}
