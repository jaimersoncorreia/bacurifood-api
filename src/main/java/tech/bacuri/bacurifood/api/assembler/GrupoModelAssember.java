package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.GrupoModel;
import tech.bacuri.bacurifood.domain.model.Grupo;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Component
public class GrupoModelAssember {

    private final ModelMapper modelMapper;

    public GrupoModel toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }

    public List<GrupoModel> toCollectionModel(Collection<Grupo> grupos) {
        return grupos.stream().map(this::toModel).toList();
    }
}
