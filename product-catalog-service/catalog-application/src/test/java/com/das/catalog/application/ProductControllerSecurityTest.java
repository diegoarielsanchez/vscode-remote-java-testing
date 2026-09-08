package com.das.catalog.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Smoke test for catalog-application — this module had no automated tests at all
 * until now. See {@code OrderControllerSecurityTest} for why this boots the full
 * context against H2 rather than using {@code @WebMvcTest}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@TestPropertySource(properties = {
        // Replace PostgreSQL with H2
        "spring.datasource.url=jdbc:h2:mem:catalogtest;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        // Disable RabbitMQ auto-configuration (no broker needed in tests)
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration",
        // Disable Eureka (no service registry needed in tests)
        "eureka.client.enabled=false",
        "eureka.client.register-with-eureka=false",
        "eureka.client.fetch-registry=false",
        // JWT secret satisfying the 32-char minimum enforced by JwtSecretValidator
        "jwt.secret=test-secret-key-for-integration-tests-only-32chars"
})
class ProductControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createEndpointRequiresAuthentication() throws Exception {
        mockMvc.perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void unmappedPathIsDenied() throws Exception {
        mockMvc.perform(get("/api/v1/products/admin-only-not-a-real-path"))
                .andExpect(status().isForbidden());
    }
}
