package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.input.CozinhaInput;
import tech.bacuri.bacurifood.domain.model.Cozinha;

@AllArgsConstructor
@Component
public class CozinhaInputDisassembler {

    private final ModelMapper mapper;

    public Cozinha toEntity(CozinhaInput cozinhaInput) {
        return mapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToEntity(CozinhaInput cozinhaInput, Cozinha cozinha) {
        mapper.map(cozinhaInput, cozinha);
    }
}
