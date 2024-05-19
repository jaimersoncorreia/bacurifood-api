package tech.bacuri.bacurifood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Objects;

public class DataFimDeveEstahDepoisDaDataInicioValidator implements ConstraintValidator<DataFimDeveEstahDepoisDaDataInicio, Object> {

    private String dataInicioField;

    private String dataFimField;

    @Override
    public void initialize(DataFimDeveEstahDepoisDaDataInicio constraint) {
        this.dataInicioField = constraint.dataInicioField();
        this.dataFimField = constraint.dataFimField();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext constraintValidatorContext) {

        try {
            LocalDate dataInicio = (LocalDate) Objects
                    .requireNonNull(BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), dataInicioField))
                    .getReadMethod().invoke(objetoValidacao);

            LocalDate dataFim = (LocalDate) Objects
                    .requireNonNull(BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), dataFimField))
                    .getReadMethod().invoke(objetoValidacao);

            if (Objects.isNull(dataInicio))
                throw new ValidationException("Data de início é obrigatória!");

            if (Objects.isNull(dataFim))
                return true;

            return dataFim.isAfter(dataInicio) || dataFim.isEqual(dataInicio);

        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
