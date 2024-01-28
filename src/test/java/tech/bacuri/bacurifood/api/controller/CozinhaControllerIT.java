package tech.bacuri.bacurifood.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.bacuri.bacurifood.BacurifoodApiApplicationIT;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.repository.CozinhaRepository;
import tech.bacuri.bacurifood.util.DatabaseCleaner;
import tech.bacuri.bacurifood.util.ResourceUtils;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

class CozinhaControllerIT extends BacurifoodApiApplicationIT {

    public static final int COZINHA_ID_INEXISTENTE = 2000;

    @Autowired
    private DatabaseCleaner cleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private int totalDeCozinhas;
    private Cozinha indiana;
    private String jsonCorretoCozinhaChinesa;

    @BeforeEach
    public void setup() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        port = getPorta();
        basePath = "/cozinhas";

        cleaner.clearTables();
        prepararDados();
        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource("/json/correto/cozinha-chinesa.json");
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
    public void deveConterTotalDeCozinhas_QuandoConsultarCozinha() {
        given()
                .accept(JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(this.totalDeCozinhas))
                .body("nome", hasItems("Indiana", "Tailandesa"));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        given()
                .body(this.jsonCorretoCozinhaChinesa)
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
                .pathParam("cozinhaId", this.indiana.getId())
                .accept(JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(OK.value())
                .body("nome", equalTo(this.indiana.getNome()));
    }

    @Test
    public void deveRetornarRespostaEStatus404_QuandoConsultarCozinhaInexistente() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                .accept(JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    private void prepararDados() {
        this.cozinhaRepository.save(Cozinha.builder().nome("Chinesa").build());
        this.indiana = this.cozinhaRepository.save(Cozinha.builder().nome("Indiana").build());
        this.cozinhaRepository.save(Cozinha.builder().nome("Tailandesa").build());
        this.cozinhaRepository.save(Cozinha.builder().nome("Brasileira").build());

        this.totalDeCozinhas = (int) cozinhaRepository.count();
    }
}