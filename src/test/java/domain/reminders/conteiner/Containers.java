//package domain.reminders.conteiner;
//
//import org.springframework.stereotype.Component;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.utility.DockerImageName;
//
//public class Containers {
//
//    @Container
//    public static final PostgreSQLContainer POSTGRES_CONTAINER = new PostgreSQLContainer<>(
//            DockerImageName.parse("postgres:15.1")
//    );
//
////    @DynamicPropertySource
////    static void postgresqlProperties(DynamicPropertyRegistry registry) {
////        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
////        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
////        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
////    }
//
//    static {
//        POSTGRES_CONTAINER.start();
//    }
//
//}
