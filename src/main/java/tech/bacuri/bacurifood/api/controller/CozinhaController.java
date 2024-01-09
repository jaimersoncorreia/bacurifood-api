package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.model.CozinhasXmlWrapper;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.repository.CozinhaRepository;
import tech.bacuri.bacurifood.domain.service.CadastroCozinhaService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;
    private final CadastroCozinhaService cadastroCozinhaService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Cozinha>> listar() {
        return ResponseEntity.ok(cadastroCozinhaService.listar());
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CozinhasXmlWrapper> listarXml() {
        return ResponseEntity.ok(new CozinhasXmlWrapper(cadastroCozinhaService.listar()));
    }

    @GetMapping("/por-nome")
    public ResponseEntity<?> buscarPorNome(@RequestParam("nome") String nome) {
        return ResponseEntity.ok(cozinhaRepository.consultarPorNome(nome));
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> obter(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cadastroCozinhaService.obter(cozinhaId);
        if (cozinha == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cozinha);
    }

    @PostMapping
    public ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha) {
        return new ResponseEntity<>(cadastroCozinhaService.salvar(cozinha), HttpStatus.CREATED);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaObtida = cadastroCozinhaService.obter(cozinhaId);
        if (cozinhaObtida == null)
            return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(cozinha, cozinhaObtida, "id");

        return new ResponseEntity<>(cadastroCozinhaService.salvar(cozinhaObtida), HttpStatus.OK);
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> deletar(@PathVariable Long cozinhaId) {
        try {
            cadastroCozinhaService.remover(cozinhaId);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.noContent().build();
    }
}
