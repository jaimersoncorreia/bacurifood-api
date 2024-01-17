package tech.bacuri.bacurifood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.exception.NegocioException;
import tech.bacuri.bacurifood.domain.model.Cidade;
import tech.bacuri.bacurifood.domain.service.CadastroCidadeService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cadastroCidadeService.listar();
    }

    @GetMapping("/{cidadeId}")
    public Cidade obter(@PathVariable Long cidadeId) {
        return cadastroCidadeService.obter(cidadeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade salvar(@RequestBody Cidade cidade) {
        try {
            return cadastroCidadeService.salvar(cidade);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage()   );
        }
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
        Cidade cidadeEncontrado = cadastroCidadeService.obter(cidadeId);
        BeanUtils.copyProperties(cidade, cidadeEncontrado, "id");

        try {
            return cadastroCidadeService.salvar(cidadeEncontrado);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidadeService.remover(cidadeId);
    }

    @PatchMapping("/{cidadeId}")
    public Cidade atualizarParcial(@PathVariable Long cidadeId, @RequestBody Map<String, Object> cidade) {
        Cidade cidadeEncontrado = cadastroCidadeService.obter(cidadeId);
        merge(cidade, cidadeEncontrado);

        return atualizar(cidadeId, cidadeEncontrado);
    }

    private void merge(Map<String, Object> dadosOrigem, Cidade cidadeDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Cidade cidadeOrigem = objectMapper.convertValue(dadosOrigem, Cidade.class);
        dadosOrigem.forEach((propriedade, valor) -> {
            Field field = ReflectionUtils.findField(Cidade.class, propriedade);
            Objects.requireNonNull(field).setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, cidadeOrigem);

            ReflectionUtils.setField(field, cidadeDestino, novoValor);
        });
    }
}
