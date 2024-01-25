package tech.bacuri.bacurifood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tech.bacuri.bacurifood.core.validation.Groups;
import tech.bacuri.bacurifood.domain.model.Restaurante;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CozinhaMixin {
    @NotNull(groups = Groups.CozinhaId.class)
    private Long id;

    @NotBlank
    private String nome;

    @JsonIgnore
    private List<Restaurante> restaurantes = new ArrayList<>();
}
