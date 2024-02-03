package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bacuri.bacurifood.api.assembler.PedidoModelAssembler;
import tech.bacuri.bacurifood.api.model.PedidoModel;
import tech.bacuri.bacurifood.domain.repository.PedidoRepository;
import tech.bacuri.bacurifood.domain.service.CadastroPedidoService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final CadastroPedidoService pedidoService;
    private final PedidoRepository pedidoRepository;
    private final PedidoModelAssembler pedidoModelAssembler;

    @GetMapping
    public List<PedidoModel> listar() {
        return pedidoModelAssembler.toCollectionModel(pedidoRepository.findAll());
    }
    @GetMapping("/{pedidoId}")
    public PedidoModel obter(@PathVariable Long pedidoId) {
        return pedidoModelAssembler.toModel(pedidoService.obter(pedidoId));
    }
}
