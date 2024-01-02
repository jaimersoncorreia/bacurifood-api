package tech.bacuri.bacurifood.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.di.service.ClienteAtivadoEvent;

@Component
public class EmissaoNotaFiscalService {
    @EventListener
    void clienteAtivadoListener(ClienteAtivadoEvent event) {
        System.out.println("Emitido Nota Fiscal para o cliente " + event.getCliente().getNome());
    }
}
