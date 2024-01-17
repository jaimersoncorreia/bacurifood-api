package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.model.CozinhasXmlWrapper;
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
        return ResponseEntity.ok(cozinhaRepository.findAllByNomeContaining(nome));
    }

    @GetMapping("/exists-por-nome")
    public ResponseEntity<?> existsPorNome(@RequestParam("nome") String nome) {
        return ResponseEntity.ok(cozinhaRepository.existsByNome(nome));
    }

    @GetMapping("/buscarPrimeiro")
    public ResponseEntity<?> buscarPrimeiro() {
        return ResponseEntity.ok(cozinhaRepository.buscarPrimeiro().orElseThrow());
    }

    @GetMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.OK)
    public Cozinha obter(@PathVariable Long cozinhaId) {
        return cadastroCozinhaService.obter(cozinhaId);
    }

    @PostMapping
    public ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha) {
        return new ResponseEntity<>(cadastroCozinhaService.salvar(cozinha), HttpStatus.CREATED);
    }

    @PutMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.OK)
    public Cozinha atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaObtida = cadastroCozinhaService.obter(cozinhaId);
        BeanUtils.copyProperties(cozinha, cozinhaObtida, "id");

        return cadastroCozinhaService.salvar(cozinhaObtida);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long cozinhaId) {
        cadastroCozinhaService.remover(cozinhaId);
    }
}
