package tech.bacuri.bacurifood.api.model.input;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CozinhaIdInput {
    @NotNull
    private Long id;
}
