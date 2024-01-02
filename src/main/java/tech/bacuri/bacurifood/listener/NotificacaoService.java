package tech.bacuri.bacurifood.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.di.notificacao.NivelUrgencia;
import tech.bacuri.bacurifood.di.notificacao.Notificador;
import tech.bacuri.bacurifood.di.notificacao.TipoDoNotificador;
import tech.bacuri.bacurifood.di.service.ClienteAtivadoEvent;

@Component
public class NotificacaoService {
    @TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
    @Autowired
    private Notificador notificador;
    @EventListener
    void clienteAtivadoListener(ClienteAtivadoEvent event) {
        notificador.notificar(event.getCliente(), "Seu cadastro no sistema está ativo!");
    }
}
