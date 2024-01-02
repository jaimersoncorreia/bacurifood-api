package tech.bacuri.bacurifood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.di.modelo.Cliente;
import tech.bacuri.bacurifood.di.notificacao.NivelUrgencia;
import tech.bacuri.bacurifood.di.notificacao.Notificador;
import tech.bacuri.bacurifood.di.notificacao.TipoDoNotificador;

@Component
public class AtivacaoClienteService {

    @TipoDoNotificador(NivelUrgencia.URGENTE)
    @Autowired
    private Notificador notificador;

    public void ativar(Cliente cliente) {
        cliente.ativar();
        notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
    }
}
