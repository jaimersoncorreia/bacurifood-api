package tech.bacuri.bacurifood.api.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.bacuri.bacurifood.core.validation.ValidacaoException;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.exception.NegocioException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.JsonMappingException.Reference;
import static org.springframework.http.HttpStatus.*;
import static tech.bacuri.bacurifood.api.exceptionhandler.ProblemType.*;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se " +
            "o problema persistir, entre em contato com o administrador do sistema.";

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Throwable cause = ExceptionUtils.getRootCause(ex);

        if (cause instanceof InvalidFormatException)
            return handleInvalidFormat((InvalidFormatException) cause, headers, status, request);

        if (cause instanceof PropertyBindingException)
            return handlePropertyBinding((PropertyBindingException) cause, headers, status, request);

        String detail = "O corpo da requisição está inválida. Verifique erro de sintaxe";
        Problem problem = createProblemBuilder(status, MENSAGEM_INCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = NOT_FOUND;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, RECURSO_NAO_ENCONTRADO, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(BAD_REQUEST, ERRO_NEGOCIO, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(CONFLICT, ENTIDADE_EM_USO, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), CONFLICT, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> hadleUncaught(Exception ex, WebRequest request) {

        HttpStatus status = INTERNAL_SERVER_ERROR;

        ex.printStackTrace();

        Problem problem = createProblemBuilder(status, ERRO_DE_SISTEMA, MSG_ERRO_GENERICA_USUARIO_FINAL)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleValidacao(ValidacaoException ex, WebRequest request) {

        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        if (body instanceof String)
            body = Problem.builder()
                    .status(status.value())
                    .title((String) body)
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .timestamp(OffsetDateTime.now())
                    .build();

        if (Objects.isNull(body))
            body = Problem.builder()
                    .status(status.value())
                    .title((status.getReasonPhrase()))
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .timestamp(OffsetDateTime.now())
                    .build();

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException)
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {

        String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.", ex.getRequestURL());

        Problem problem = createProblemBuilder(status, RECURSO_NAO_ENCONTRADO, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }


    private ResponseEntity<Object> handleValidationInternal(Exception ex,
                                                            BindingResult bindingResult,
                                                            HttpHeaders headers,
                                                            HttpStatus status,
                                                            WebRequest request) {
        String detail = String.format("Um ou mais campos estão %s. Faça o preenchimento correto e tente novamente.", "inválidos");

        List<Problem.Object> objects = bindingResult
                .getAllErrors()
                .stream()
                .map(this::getErrors)
                .toList();

        Problem problem = createProblemBuilder(status, DADOS_INVALIDOS, detail)
                .userMessage(detail)
                .objects(objects)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    private Problem.Object getErrors(ObjectError objectError) {
        String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

        String name = objectError.getObjectName();
        if (objectError instanceof FieldError)
            name = ((FieldError) objectError).getField();

        return Problem.Object.builder()
                .name(name)
                .userMessage(message)
                .build();
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {
        String detail = String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade.", getPath(ex.getPath()));
        Problem problem = createProblemBuilder(status, PROPRIEDADE_NAO_EXISTE, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
                                                       HttpHeaders headers,
                                                       HttpStatus status,
                                                       WebRequest request) {
        String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é de tipo inválido. Corrija e " +
                "informe um valor compatível com o tipo '%s'.", getPath(ex.getPath()), ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status, MENSAGEM_INCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatus status,
                                                                    WebRequest request) {

        String tipoVariavel = Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é de tipo inválido. Corrija e " +
                "informe um valor compatível com o tipo '%s'.", ex.getName(), ex.getValue(), tipoVariavel);

        Problem problem = createProblemBuilder(status, PARAMETRO_INVALIDO, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private static String getPath(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }
}
