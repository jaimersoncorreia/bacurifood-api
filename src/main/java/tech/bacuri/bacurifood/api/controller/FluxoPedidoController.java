package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.domain.service.FluxoPedidoServide;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidos/{pedidoCodigo}")
public class FluxoPedidoController {

    private final FluxoPedidoServide fluxoPedido;

    @PutMapping("/confirmacao")
    @ResponseStatus(NO_CONTENT)
    public void confirmar(@PathVariable String pedidoCodigo) {
        fluxoPedido.confirmar(pedidoCodigo);
    }

    @PutMapping("/entrega")
    @ResponseStatus(NO_CONTENT)
    public void entregar(@PathVariable String pedidoCodigo) {
        fluxoPedido.entregar(pedidoCodigo);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(NO_CONTENT)
    public void cancelar(@PathVariable String pedidoCodigo) {
        fluxoPedido.cancelar(pedidoCodigo);
    }
}
