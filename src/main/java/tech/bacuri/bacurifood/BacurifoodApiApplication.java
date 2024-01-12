package tech.bacuri.bacurifood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tech.bacuri.bacurifood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class BacurifoodApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BacurifoodApiApplication.class, args);
    }

}
