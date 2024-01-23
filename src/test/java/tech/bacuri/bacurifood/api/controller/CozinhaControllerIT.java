package tech.bacuri.bacurifood.api.controller;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CozinhaControllerIT {

    @LocalServerPort
    private int porta;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setup() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        port = porta;
        basePath = "/cozinhas";

        flyway.migrate();
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
}