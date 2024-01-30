package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.UsuarioModel;
import tech.bacuri.bacurifood.domain.model.Usuario;

import java.util.List;

@AllArgsConstructor
@Component
public class UsuarioModelAssembler {
    private final ModelMapper modelMapper;

    public UsuarioModel toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModel.class);
    }

    public List<UsuarioModel> toCollectionModel(List<Usuario> usuario) {
        return usuario.stream().map(this::toModel).toList();
    }
}
