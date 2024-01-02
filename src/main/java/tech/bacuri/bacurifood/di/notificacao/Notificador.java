package tech.bacuri.bacurifood.di.notificacao;

import tech.bacuri.bacurifood.di.modelo.Cliente;

public interface Notificador {
    void notificar(Cliente cliente, String mensagem);
}
