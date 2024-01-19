package tech.bacuri.bacurifood.api.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.exception.NegocioException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.JsonMappingException.Reference;
import static org.springframework.http.HttpStatus.*;
import static tech.bacuri.bacurifood.api.exceptionhandler.ProblemType.*;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Throwable cause = ExceptionUtils.getRootCause(ex);

        if (cause instanceof InvalidFormatException)
            return handleInvalidFormatException((InvalidFormatException) cause, headers, status, request);

        if (cause instanceof PropertyBindingException)
            return handlePropertyBindingException((PropertyBindingException) cause, headers, status, request);

        String detail = "O corpo da requisição está inválida. Verifique erro de sintaxe";
        Problem problem = createProblemBuilder(status, MENSAGEM_INCOMPREENSIVEL, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                                                                       HttpHeaders headers,
                                                                       HttpStatus status,
                                                                       WebRequest request) {
        String detail = String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade.", getPath(ex.getPath()));
        Problem problem = createProblemBuilder(status, PROPRIEDADE_NAO_EXISTE, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
        String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é de tipo inválido. Corrija e " +
                "informe um valor compatível com o tipo '%s'.", getPath(ex.getPath()), ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status, MENSAGEM_INCOMPREENSIVEL, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private static String getPath(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = NOT_FOUND;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, ENTIDADE_NAO_ENCONTRADA, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(BAD_REQUEST, ERRO_NEGOCIO, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(CONFLICT, ENTIDADE_EM_USO, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        if (body instanceof String)
            body = Problem.builder().status(status.value()).title((String) body).build();

        if (Objects.isNull(body))
            body = Problem.builder().status(status.value()).title((status.getReasonPhrase())).build();

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }
}
