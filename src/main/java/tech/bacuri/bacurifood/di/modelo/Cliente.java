package tech.bacuri.bacurifood.di.modelo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private String nome;
    private String email;
    private String telefone;
    private boolean ativo = false;

    public void ativar() {
        setAtivo(true);
    }
}
