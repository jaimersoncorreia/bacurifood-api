package tech.bacuri.bacurifood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tech.bacuri.bacurifood.domain.model.Grupo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioMixin {
    private Long id;

    private String nome;

    private String email;

    @JsonIgnore
    private String senha;

    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private List<Grupo> grupos = new ArrayList<>();
}
