package tech.bacuri.bacurifood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeIdInput {
    @NotNull
    private Long id;
}
