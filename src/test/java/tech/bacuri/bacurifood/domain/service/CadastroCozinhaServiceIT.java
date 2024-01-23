package tech.bacuri.bacurifood.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.bacuri.bacurifood.domain.exception.CozinhaNaoEncontradaException;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.model.Cozinha;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CadastroCozinhaServiceIT {
    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Test
    void deveAtribuirId_QuandoCadastrarCozinha_ComDadosCorretos() {
        //cenário
        Cozinha novaCozinha = Cozinha.builder()
                .nome("Chinesa")
                .build();

        //ação
        novaCozinha = cozinhaService.salvar(novaCozinha);

        //validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test
    void deveLancarException_QuandoCadastrarCozinha_SemNome() {
        //cenário
        Cozinha novaCozinha = Cozinha.builder()
                .nome(null)
                .build();

        //ação
        ConstraintViolationException erroEsperado = Assertions
                .assertThrows(ConstraintViolationException.class, () -> cozinhaService.salvar(novaCozinha));

        //validação
        assertThat(erroEsperado).isNotNull();
    }

    @Test
    void deveLancarException_QuandoExcluirCozinha_EmUso() {
        //cenário
        Long cozinhaId = 1L;

        //ação
        EntidadeEmUsoException erroEsperado = Assertions
                .assertThrows(EntidadeEmUsoException.class, () -> cozinhaService.remover(cozinhaId));

        //validação
        assertThat(erroEsperado).isNotNull();
    }

    @Test
    void deveLancarException_QuandoExcluirCozinha_Inexistente() {
        //cenário
        Long cozinhaId = 100L;

        //ação
        CozinhaNaoEncontradaException erroEsperado = Assertions
                .assertThrows(CozinhaNaoEncontradaException.class, () -> cozinhaService.remover(cozinhaId));

        //validação
        assertThat(erroEsperado).isNotNull();
    }
}