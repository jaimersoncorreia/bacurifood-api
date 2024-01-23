package tech.bacuri.bacurifood.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CozinhaControllerIT {

    @LocalServerPort
    private int port;

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinha() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        given()
                .basePath("/cozinhas")
                .port(port)
                .accept(JSON)
                .when()
                .get()
                .then()
                .statusCode(OK.value());
    }
}