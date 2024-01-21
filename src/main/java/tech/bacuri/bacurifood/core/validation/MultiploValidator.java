package tech.bacuri.bacurifood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {
    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext context) {
        boolean valido = true;

        if (number != null) {
            BigDecimal valor = BigDecimal.valueOf(number.doubleValue());
            BigDecimal multiplo = BigDecimal.valueOf(this.numeroMultiplo);
            BigDecimal resto = valor.remainder(multiplo);
            valido = BigDecimal.ZERO.compareTo(resto) == 0;
        }

        return valido;
    }
}
