package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.PermissaoModel;
import tech.bacuri.bacurifood.domain.model.Permissao;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Component
public class PermissaoModelAssember {

    private final ModelMapper modelMapper;

    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    public List<PermissaoModel> toCollectionModel(Collection<Permissao> permissoes) {
        return permissoes.stream().map(this::toModel).toList();
    }
}
