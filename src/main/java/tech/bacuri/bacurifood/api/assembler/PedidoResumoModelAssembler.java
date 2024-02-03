package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.PedidoResumoModel;
import tech.bacuri.bacurifood.domain.model.Pedido;

import java.util.List;

@AllArgsConstructor
@Component
public class PedidoResumoModelAssembler {
    private final ModelMapper modelMapper;

    public PedidoResumoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoModel.class);
    }

    public List<PedidoResumoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream().map(this::toModel).toList();
    }
}
