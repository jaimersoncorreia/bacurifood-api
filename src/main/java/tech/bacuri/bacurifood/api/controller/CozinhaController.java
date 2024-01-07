package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.model.CozinhasXmlWrapper;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.repository.CozinhaRepository;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Cozinha>> listar() {
        return ResponseEntity.ok(cozinhaRepository.listar());
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CozinhasXmlWrapper> listarXml() {
        return ResponseEntity.ok(new CozinhasXmlWrapper(cozinhaRepository.listar()));
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> obter(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaRepository.obter(cozinhaId);
        if (cozinha == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cozinha);
    }

    @PostMapping
    public ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha) {
        return new ResponseEntity<>(cozinhaRepository.salvar(cozinha), HttpStatus.CREATED);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaObtida = cozinhaRepository.obter(cozinhaId);
        if (cozinhaObtida == null)
            return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(cozinha, cozinhaObtida, "id");

        return new ResponseEntity<>(cozinhaRepository.salvar(cozinhaObtida), HttpStatus.OK);
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> deletar(@PathVariable Long cozinhaId) {
        Cozinha cozinhaObtida = cozinhaRepository.obter(cozinhaId);
        if (cozinhaObtida == null)
            return ResponseEntity.notFound().build();

        try {
            cozinhaRepository.remover(cozinhaObtida);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return ResponseEntity.noContent().build();
    }
}
