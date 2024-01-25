package tech.bacuri.bacurifood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tech.bacuri.bacurifood.domain.model.Permissao;

import java.util.ArrayList;
import java.util.List;

public class GrupoMixin {
    private Long id;

    private String nome;

    @JsonIgnore
    private List<Permissao> permissoes = new ArrayList<>();
}
