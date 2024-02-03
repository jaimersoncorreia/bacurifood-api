package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.PedidoModel;
import tech.bacuri.bacurifood.domain.model.Pedido;

import java.util.List;

@AllArgsConstructor
@Component
public class PedidoModelAssembler {
    private final ModelMapper modelMapper;

    public PedidoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModel.class);
    }

    public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream().map(this::toModel).toList();
    }
}
