package tech.bacuri.bacurifood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.mixin.RestauranteMixin;
import tech.bacuri.bacurifood.domain.model.Restaurante;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }

}
