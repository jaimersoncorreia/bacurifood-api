package tech.bacuri.bacurifood.domain.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum StatusPedido {
    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO);

    private final String descricao;
    private final List<StatusPedido> statusAnteriores;

    StatusPedido(String descricao, StatusPedido... statusAnterios) {
        this.descricao = descricao;
        this.statusAnteriores = Arrays.asList(statusAnterios);
    }

    public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
        return !novoStatus.statusAnteriores.contains(this);
    }
}
