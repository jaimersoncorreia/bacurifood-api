package tech.bacuri.bacurifood.api.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
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
import tech.bacuri.bacurifood.domain.service.EmissaoPedidoService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final EmissaoPedidoService pedidoService;
    private final PedidoRepository pedidoRepository;
    private final PedidoModelAssembler pedidoModelAssembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;
    private final PedidoInputDisassembler pedidoInputDisassembler;

    @GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidoRepository.findAll());
        MappingJacksonValue wrapper = new MappingJacksonValue(pedidosModel);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
        if (StringUtils.isNotBlank(campos)) {
            System.out.println("campos = " + campos);
            filterProvider
                    .addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.replace(" ", "").split(",")));
        }
        wrapper.setFilters(filterProvider);
        return wrapper;
    }
    /*
    @GetMapping
    public List<PedidoResumoModel> listar() {
        return pedidoResumoModelAssembler.toCollectionModel(pedidoRepository.findAll());
    }
    */

    @GetMapping("/{pedidoCodigo}")
    public PedidoModel obter(@PathVariable String pedidoCodigo) {
        return pedidoModelAssembler.toModel(pedidoService.obter(pedidoCodigo));
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
