package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.input.GrupoInput;
import tech.bacuri.bacurifood.domain.model.Grupo;

@AllArgsConstructor
@Component
public class GrupoInputDisassembler {

    private final ModelMapper mapper;

    public Grupo toEntity(GrupoInput grupoInput) {
        return mapper.map(grupoInput, Grupo.class);
    }

    public void copyToEntity(GrupoInput grupoInput, Grupo restaurante) {
        mapper.map(grupoInput, restaurante);
    }
}
