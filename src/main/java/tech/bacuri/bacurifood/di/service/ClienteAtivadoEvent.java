package tech.bacuri.bacurifood.di.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.bacuri.bacurifood.di.modelo.Cliente;

@Getter
@AllArgsConstructor
public class ClienteAtivadoEvent {
    private Cliente cliente;
}
