package tech.bacuri.bacurifood.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {DataFimDeveEstahDepoisDaDataInicioValidator.class})
public @interface DataFimDeveEstahDepoisDaDataInicio {
    String message() default "A data fim deve está depois a data de início";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dataInicioField();

    String dataFimField();
}
