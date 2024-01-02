package tech.bacuri.bacurifood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.bacuri.bacurifood.di.modelo.Cliente;
import tech.bacuri.bacurifood.di.notificacao.NivelUrgencia;
import tech.bacuri.bacurifood.di.notificacao.TipoDoNotificador;
import tech.bacuri.bacurifood.di.service.AtivacaoClienteService;

@Controller
public class MeuPrimeiroController {

    @Autowired
    private AtivacaoClienteService ativacaoClienteService;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        Cliente joao = Cliente.builder()
                .nome("João")
                .email("joao@xyz.tech")
                .telefone("99 99999 9999")
                .build();

        ativacaoClienteService.ativar(joao);

        return "Olá";
    }
}
