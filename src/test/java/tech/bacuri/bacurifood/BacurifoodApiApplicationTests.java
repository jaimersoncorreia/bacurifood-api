package tech.bacuri.bacurifood;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.bacuri.bacurifood.domain.service.CadastroCozinhaService;

import static org.junit.jupiter.api.Assertions.assertFalse;

@AllArgsConstructor
@SpringBootTest
class BacurifoodApiApplicationTests {
    private final CadastroCozinhaService cozinhaService;

    @Test
    void contextLoads() {
        assertFalse(false);
    }

}
