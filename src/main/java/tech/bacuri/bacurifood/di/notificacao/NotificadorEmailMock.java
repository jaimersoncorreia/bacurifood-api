package tech.bacuri.bacurifood.di.notificacao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.di.modelo.Cliente;

@Profile("dev")
@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmailMock implements Notificador {
    @Value("${notificador.email.host-servido}")
    private String host;

    @Value("${notificador.email.porta-servidor}")
    private Integer porta;

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.println("host = " + host);
        System.out.println("porta = " + porta);
        System.out.printf("MOCK: Notificação seria enviada para %s através do e-mail %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
