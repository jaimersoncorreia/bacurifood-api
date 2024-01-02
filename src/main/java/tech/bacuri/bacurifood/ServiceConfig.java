package tech.bacuri.bacurifood;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.bacuri.bacurifood.di.service.AtivacaoClienteService;

@Configuration
public class ServiceConfig {
    @Bean(initMethod = "init", destroyMethod = "destroy")
    AtivacaoClienteService ativacaoClienteService() {
        return new AtivacaoClienteService();
    }
}
