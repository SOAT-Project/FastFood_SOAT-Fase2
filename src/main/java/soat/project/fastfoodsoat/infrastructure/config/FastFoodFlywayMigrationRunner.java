package soat.project.fastfoodsoat.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

@Component
public class FastFoodFlywayMigrationRunner {

    private final Flyway flyway;

    public FastFoodFlywayMigrationRunner(Flyway flyway) {
        this.flyway = flyway;
    }

    @PostConstruct
    public void migrate() {
        flyway.migrate();
    }
}