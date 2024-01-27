package tech.bacuri.bacurifood.api.model.input;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CozinhaIdInput {
    @NotNull
    private Long id;
}
