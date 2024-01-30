package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.input.UsuarioInput;
import tech.bacuri.bacurifood.domain.model.Usuario;

@AllArgsConstructor
@Component
public class UsuarioInputDisassembler {

    private final ModelMapper mapper;

    public Usuario toEntity(UsuarioInput usuarioInput) {
        return mapper.map(usuarioInput, Usuario.class);
    }

    public void copyToEntity(UsuarioInput usuarioInput, Usuario usuario) {
        mapper.map(usuarioInput, usuario);
    }
}
