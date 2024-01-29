package tech.bacuri.bacurifood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.bacuri.bacurifood.api.model.EnderecoModel;
import tech.bacuri.bacurifood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        toEnderecoModelTypeMap(modelMapper);
        return modelMapper;
    }

    private static void toEnderecoModelTypeMap(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Endereco.class, EnderecoModel.class)
                .addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                        (enderecoDest, value) -> enderecoDest.getCidade().setEstado((String) value));
    }
}
