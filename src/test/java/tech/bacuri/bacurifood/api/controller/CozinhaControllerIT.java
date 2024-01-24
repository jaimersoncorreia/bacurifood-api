package tech.bacuri.bacurifood.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.repository.CozinhaRepository;
import tech.bacuri.bacurifood.util.DatabaseCleaner;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CozinhaControllerIT {

    @LocalServerPort
    private int porta;

    @Autowired
    private DatabaseCleaner cleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @BeforeEach
    public void setup() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        port = porta;
        basePath = "/cozinhas";

        cleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinha() {
        given()
                .accept(JSON)
                .when()
                .get()
                .then()
                .statusCode(OK.value());
    }

    @Test
    public void deveConter4Cozinhas_QuandoConsultarCozinha() {
        given()
                .accept(JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(4))
                .body("nome", hasItems("Indiana", "Tailandesa"));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        given()
                .body("{\"nome\": \"Chinesa\"}")
                .contentType(JSON)
                .accept(JSON)
                .when()
                .post()
                .then()
                .statusCode(CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorreto_QuandoConsultarCozinhaExistente() {
        given()
                .pathParam("cozinhaId", 2)
                .accept(JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(OK.value())
                .body("nome", equalTo("Indiana"));
    }

    @Test
    public void deveRetornarRespostaEStatus404_QuandoConsultarCozinhaInexistente() {
        given()
                .pathParam("cozinhaId", 20)
                .accept(JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    private void prepararDados() {
        cozinhaRepository.save(Cozinha.builder().nome("Chinesa").build());
        cozinhaRepository.save(Cozinha.builder().nome("Indiana").build());
        cozinhaRepository.save(Cozinha.builder().nome("Tailandesa").build());
        cozinhaRepository.save(Cozinha.builder().nome("Brasileira").build());
    }
}