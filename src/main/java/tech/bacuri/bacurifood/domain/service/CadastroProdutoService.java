package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.bacurifood.domain.exception.ProdutoNaoEncontradoException;
import tech.bacuri.bacurifood.domain.model.Produto;
import tech.bacuri.bacurifood.domain.repository.ProdutoRepository;

@AllArgsConstructor
@Service
public class CadastroProdutoService {
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto obter(Long restauranteId, Long produtoId) {
        return produtoRepository.findByRestauranteIdAndId(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
    }
}
