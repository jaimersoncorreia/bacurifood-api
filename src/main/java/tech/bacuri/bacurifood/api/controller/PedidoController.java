package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.assembler.PedidoInputDisassembler;
import tech.bacuri.bacurifood.api.assembler.PedidoModelAssembler;
import tech.bacuri.bacurifood.api.assembler.PedidoResumoModelAssembler;
import tech.bacuri.bacurifood.api.model.PedidoModel;
import tech.bacuri.bacurifood.api.model.PedidoResumoModel;
import tech.bacuri.bacurifood.api.model.input.PedidoInput;
import tech.bacuri.bacurifood.domain.model.Pedido;
import tech.bacuri.bacurifood.domain.model.Usuario;
import tech.bacuri.bacurifood.domain.repository.PedidoRepository;
import tech.bacuri.bacurifood.domain.service.CadastroPedidoService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final CadastroPedidoService pedidoService;
    private final PedidoRepository pedidoRepository;
    private final PedidoModelAssembler pedidoModelAssembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;
    private final PedidoInputDisassembler pedidoInputDisassembler;

    @GetMapping
    public List<PedidoResumoModel> listar() {
        return pedidoResumoModelAssembler.toCollectionModel(pedidoRepository.findAll());
    }

    @GetMapping("/{pedidoId}")
    public PedidoModel obter(@PathVariable Long pedidoId) {
        return pedidoModelAssembler.toModel(pedidoService.obter(pedidoId));
    }

    @PostMapping
    public PedidoModel salvar(@RequestBody @Valid PedidoInput pedidoInput) {

        Pedido pedido = pedidoInputDisassembler.toEntity(pedidoInput);
        Usuario cliente = new Usuario();
        cliente.setId(1L);
        pedido.setCliente(cliente);

        return pedidoModelAssembler.toModel(pedidoService.salvar(pedido));
    }
}
