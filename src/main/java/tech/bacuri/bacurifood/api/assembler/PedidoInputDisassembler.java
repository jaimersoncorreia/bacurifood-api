package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.input.PedidoInput;
import tech.bacuri.bacurifood.domain.model.Pedido;

@AllArgsConstructor
@Component
public class PedidoInputDisassembler {

    private final ModelMapper mapper;

    public Pedido toEntity(PedidoInput pedidoInput) {
        return mapper.map(pedidoInput, Pedido.class);
    }

    public void copyToEntity(PedidoInput pedidoInput, Pedido pedido) {
        mapper.map(pedidoInput, pedido);
    }

}
