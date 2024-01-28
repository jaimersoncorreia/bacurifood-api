package tech.bacuri.bacurifood;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

@Getter
@Setter
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BacurifoodApiApplication.class)
@TestPropertySource("/application-test.properties")
public class BacurifoodApiApplicationIT {
    @LocalServerPort
    private int porta;

    @Test
    void contextLoads() {
    }

}
