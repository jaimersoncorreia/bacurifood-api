package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.input.CidadeInput;
import tech.bacuri.bacurifood.domain.model.Cidade;
import tech.bacuri.bacurifood.domain.model.Estado;

@AllArgsConstructor
@Component
public class CidadeInputDisassembler {
    public ModelMapper mapper;

    public Cidade toEntity(CidadeInput cidadeInput) {
        return mapper.map(cidadeInput, Cidade.class);
    }

    public void copyToEntity(CidadeInput cidadeInput, Cidade cidade) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // tech.bacuri.bacurifood.domain.model.Estado was altered from 2 to 1
        cidade.setEstado(new Estado());
        mapper.map(cidadeInput, cidade);
    }
}
