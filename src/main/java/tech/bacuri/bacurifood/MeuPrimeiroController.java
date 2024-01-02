package tech.bacuri.bacurifood;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.bacuri.bacurifood.di.modelo.Cliente;
import tech.bacuri.bacurifood.di.service.AtivacaoClienteService;


@Controller
@AllArgsConstructor
public class MeuPrimeiroController {

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
