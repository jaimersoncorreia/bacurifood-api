package tech.bacuri.bacurifood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class Problema {
    private LocalDateTime dataHora;
    private String mensagem;
}
