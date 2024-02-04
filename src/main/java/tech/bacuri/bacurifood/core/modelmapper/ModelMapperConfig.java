package tech.bacuri.bacurifood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.bacuri.bacurifood.api.model.EnderecoModel;
import tech.bacuri.bacurifood.api.model.input.ItemPedidoInput;
import tech.bacuri.bacurifood.domain.model.Endereco;
import tech.bacuri.bacurifood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        toEnderecoModelTypeMap(modelMapper);
        toItemPedidoInputTypeMap(modelMapper);
        return modelMapper;
    }

    private static void toItemPedidoInputTypeMap(ModelMapper modelMapper) {
        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));
    }

    private static void toEnderecoModelTypeMap(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Endereco.class, EnderecoModel.class)
                .addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                        (enderecoDest, value) -> enderecoDest.getCidade().setEstado((String) value));
    }
}
