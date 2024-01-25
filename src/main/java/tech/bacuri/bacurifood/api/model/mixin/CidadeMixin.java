package tech.bacuri.bacurifood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tech.bacuri.bacurifood.core.validation.Groups;
import tech.bacuri.bacurifood.domain.model.Estado;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

public class CidadeMixin {
    private Long id;

    @NotBlank
    private String nome;

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    @Valid
    @NotNull
    @ConvertGroup(to = Groups.EstadoId.class)
    private Estado estado;
}
