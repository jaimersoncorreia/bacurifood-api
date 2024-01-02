package tech.bacuri.bacurifood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.di.modelo.Cliente;
import tech.bacuri.bacurifood.di.notificacao.NivelUrgencia;
import tech.bacuri.bacurifood.di.notificacao.Notificador;
import tech.bacuri.bacurifood.di.notificacao.TipoDoNotificador;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@Component
public class AtivacaoClienteService {

    @TipoDoNotificador(NivelUrgencia.URGENTE)
    @Autowired
    private Notificador notificador;

//    @PostConstruct
    public void init() {
        System.out.println("INIT");
    }

//    @PreDestroy
    public void destroy() {
        System.out.println("DESTROY");
    }

    public void ativar(Cliente cliente) {
        cliente.ativar();
        notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
    }
}
