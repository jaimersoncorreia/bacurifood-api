package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.exception.FormaPagamentoNaoEncontradaException;
import tech.bacuri.bacurifood.domain.model.FormaPagamento;
import tech.bacuri.bacurifood.domain.repository.FormaPagamentoRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CadastroFormaPagamentoService {
    private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento de código %d não pode ser removida, pois está em uso";
    private final FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> listar() {
        return formaPagamentoRepository.findAll();
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    public FormaPagamento obter(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
    }

    @Transactional
    public FormaPagamento atualizar(FormaPagamento formaPagamento) {
        return salvar(formaPagamento);
    }

    public void deletar(Long formaPagamentoId) {
        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
            formaPagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
        } catch (DataIntegrityViolationException e) {
            String msg = String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId);
            throw new EntidadeEmUsoException(msg);
        }
    }
}
