package tech.bacuri.bacurifood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro inválido"),
    PROPRIEDADE_NAO_EXISTE("/propriedade-nao-existe", "Propriedade não existe"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negógio");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.title = title;
        this.uri = "http://api.bacurifood.local:8081" + path;
    }
}
