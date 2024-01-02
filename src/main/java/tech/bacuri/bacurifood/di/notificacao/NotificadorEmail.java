package tech.bacuri.bacurifood.di.notificacao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.di.modelo.Cliente;

@Profile("prod")
@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmail implements Notificador {
    public NotificadorEmail() {
        System.out.println("NotificadorEmail REAL");
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf("Notificado %s através do e-mail %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
