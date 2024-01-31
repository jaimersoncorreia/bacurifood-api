package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.assembler.ProdutoInputDisassembler;
import tech.bacuri.bacurifood.api.assembler.ProdutoModelAssembler;
import tech.bacuri.bacurifood.api.model.ProdutoModel;
import tech.bacuri.bacurifood.api.model.input.ProdutoInput;
import tech.bacuri.bacurifood.domain.model.Produto;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.service.CadastroProdutoService;
import tech.bacuri.bacurifood.domain.service.CadastroRestauranteService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private final CadastroRestauranteService restauranteService;
    private final ProdutoModelAssembler produtoModelAssembler;
    private final ProdutoInputDisassembler produtoInputDisassembler;
    private final CadastroProdutoService produtoService;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.obter(restauranteId);
        return produtoModelAssembler.toCollectionModel(restaurante.getProdutos());
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel obterProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.obter(restauranteId, produtoId);
        return produtoModelAssembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ProdutoModel salvarProduto(@PathVariable Long restauranteId,
                                      @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.obter(restauranteId);
        Produto produto = produtoInputDisassembler.toEntity(produtoInput);
        produto.setRestaurante(restaurante);

        return produtoModelAssembler.toModel(produtoService.salvar(produto));
    }

    @PutMapping("/{produtoId}")
    public ProdutoModel atualizarProduto(@PathVariable Long restauranteId,
                                         @PathVariable Long produtoId,
                                         @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoObtido = produtoService.obter(restauranteId, produtoId);
        produtoInputDisassembler.copyToEntity(produtoInput, produtoObtido);
        return produtoModelAssembler.toModel(produtoService.salvar(produtoObtido));
    }
}
