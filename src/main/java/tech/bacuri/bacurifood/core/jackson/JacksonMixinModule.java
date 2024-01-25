package tech.bacuri.bacurifood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.mixin.*;
import tech.bacuri.bacurifood.domain.model.*;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Grupo.class, GrupoMixin.class);
        setMixInAnnotation(Pedido.class, PedidoMixin.class);
        setMixInAnnotation(Usuario.class, UsuarioMixin.class);
    }

}
