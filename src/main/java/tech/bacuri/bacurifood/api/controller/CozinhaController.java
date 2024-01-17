package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
