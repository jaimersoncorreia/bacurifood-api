package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.domain.service.FluxoPedidoServide;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class FluxoPedidoController {

    private final FluxoPedidoServide fluxoPedido;

    @PutMapping("/confirmacao")
    @ResponseStatus(NO_CONTENT)
    public void confirmar(@PathVariable Long pedidoId) {
        fluxoPedido.confirmar(pedidoId);
    }
}
