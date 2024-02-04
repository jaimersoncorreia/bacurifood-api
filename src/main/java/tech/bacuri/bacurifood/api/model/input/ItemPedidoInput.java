package tech.bacuri.bacurifood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemPedidoInput {

    @NotNull
    private Long produtoId;// /*checar se o produto é do restaurante*/

    @NotNull
    private Integer quantidade;

    private String observacao;
}
