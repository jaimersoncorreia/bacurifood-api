package tech.bacuri.bacurifood.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.repository.CozinhaRepository;
import tech.bacuri.bacurifood.domain.repository.RestauranteRepository;
import tech.bacuri.bacurifood.util.DatabaseCleaner;
import tech.bacuri.bacurifood.util.ResourceUtils;

import java.math.BigDecimal;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class RestauranteControllerIT {

    private static final Long RESTAURANTE_ID_INEXISTENTE = 200L;
    @LocalServerPort
    private int porta;

    @Autowired
    private DatabaseCleaner cleaner;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private String restauranteTukTukComidaIndiana;
    private Restaurante thaiDelivery;
    private String restauranteLancarBadRequest;
    private String restauranteLancarBadRequestRestauranteSemCozinha;
    private String restauranteLancarBadRequestRestauranteSemCozinhaId;

    @BeforeEach
    public void setup() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        port = porta;
        basePath = "/restaurantes";

        cleaner.clearTables();
        prepararDados();
        restauranteTukTukComidaIndiana = ResourceUtils.getContentFromResource("/json/correto/restaurante-tuk-tuk-comida-indiana.json");
        restauranteLancarBadRequest = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-lancar-bad_request.json");
        restauranteLancarBadRequestRestauranteSemCozinha = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-lancar-bad_request-restaurante-sem-cozinha.json");
        restauranteLancarBadRequestRestauranteSemCozinhaId = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-lancar-bad_request-restaurante-sem-cozinha-id.json");
    }

    @Test
    void deveObterOK_QuandoConsultarPorRestauranteId() {
        given()
                .pathParam("restauranteId", this.thaiDelivery.getId())
                .accept(JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(OK.value())
                .body("nome", equalTo(this.thaiDelivery.getNome()));
    }

    @Test
    void deveObterNOT_FOUND_QuandoConsultarPorRestauranteIdInexistente() {
        given()
                .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
                .accept(JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        given()
                .body(this.restauranteTukTukComidaIndiana)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .post()
                .then()
                .statusCode(CREATED.value());
    }

    @Test
    public void deveRetornarBAD_REQUEST_QuandoCadastrarRestauranteComFreteGratisSemOnomeFreteGratis() {
        given()
                .body(restauranteLancarBadRequest)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .post()
                .then()
                .statusCode(BAD_REQUEST.value())
                .body("title", containsString("Dados inválidos"));
    }

    @Test
    public void deveRetornarBAD_REQUEST_QuandoCadastrarRestauranteSemCozinha() {
        given()
                .body(restauranteLancarBadRequestRestauranteSemCozinha)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .post()
                .then()
                .statusCode(BAD_REQUEST.value())
                .body("objects[0].userMessage", containsString("Cozinha é obrigatório"));
    }

    @Test
    public void deveRetornarBAD_REQUEST_QuandoCadastrarRestauranteSemCozinhaId() {
        given()
                .body(restauranteLancarBadRequestRestauranteSemCozinhaId)
                .contentType(JSON)
                .accept(JSON)
                .when()
                .post()
                .then()
                .statusCode(BAD_REQUEST.value())
                .body("objects[0].userMessage", containsString("Códido da cozinha é obrigatório"));
    }

    private void prepararDados() {
        Cozinha indiana = cozinhaRepository.save(Cozinha.builder().nome("Indiana").build());
        this.thaiDelivery = restauranteRepository.save(Restaurante.builder()
                .nome("Thai Delivery")
                .taxaFrete(BigDecimal.valueOf(15.00))
                .cozinha(indiana)
                .build());

    }
}