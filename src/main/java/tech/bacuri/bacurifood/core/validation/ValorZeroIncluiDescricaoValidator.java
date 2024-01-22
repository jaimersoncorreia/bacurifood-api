package tech.bacuri.bacurifood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Objects;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {
    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        this.valorField = constraintAnnotation.valorField();
        this.descricaoField = constraintAnnotation.descricaoField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objectValidacao, ConstraintValidatorContext constraintValidatorContext) {
        BigDecimal valor = (BigDecimal) getObject(objectValidacao, valorField);
        String descricao = (String) getObject(objectValidacao, descricaoField);

        if (Objects.nonNull(valor) && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null)
            return descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());

        return true;
    }

    private Object getObject(Object objectValidacao, String campo) {
        try {
            return Objects.requireNonNull(BeanUtils.getPropertyDescriptor(objectValidacao.getClass(), campo))
                    .getReadMethod().invoke(objectValidacao);
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
