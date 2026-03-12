package com.das.cleanddd.application;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class VisitErrorResponseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnStandardErrorPayloadForDomainException() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(get("/api/v1/visit/get")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content("""
                        {
                          "visitId": "%s"
                        }
                        """.formatted(UUID.randomUUID())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.statusCode").value(400))
            .andExpect(jsonPath("$.message").value("Visit not found."));
    }

                @Test
                void shouldReturnStandardErrorPayloadWhenVisitDateIsInFuture() throws Exception {
                                String token = loginAndGetToken();

                                String healthCareProfId = createHealthCareProfAndGetId(token);
                                String medicalSalesRepId = createMedicalSalesRepAndGetId(token);
                                activateHealthCareProf(token, healthCareProfId);
                                activateMedicalSalesRep(token, medicalSalesRepId);

                                String futureDate = LocalDate.now().plusDays(1).toString();
                                String visitSiteId = UUID.randomUUID().toString();

                                mockMvc.perform(post("/api/v1/visit/create")
                                                                .header("Authorization", "Bearer " + token)
                                                                .contentType(APPLICATION_JSON)
                                                                .content("""
                                                                                                {
                                                                                                        "visitDate": "%s",
                                                                                                        "healthCareProfId": "%s",
                                                                                                        "visitComments": "future date validation",
                                                                                                        "visitSiteId": "%s",
                                                                                                        "medicalSalesRepId": "%s"
                                                                                                }
                                                                                                """.formatted(futureDate, healthCareProfId, visitSiteId, medicalSalesRepId)))
                                                .andExpect(status().isBadRequest())
                                                .andExpect(jsonPath("$.statusCode").value(400))
                                                .andExpect(jsonPath("$.message").value("Visit date cannot be later than today."));
                }

                private String loginAndGetToken() throws Exception {
                                MvcResult loginResult = mockMvc.perform(post("/auth/login")
                                                                .contentType(APPLICATION_JSON)
                                                                .content("""
                                                                                                {
                                                                                                        "username": "user",
                                                                                                        "password": "Apatehia65$"
                                                                                                }
                                                                                                """))
                                                .andExpect(status().isOk())
                                                .andReturn();

                                JsonNode loginResponse = objectMapper.readTree(loginResult.getResponse().getContentAsString());
                                return loginResponse.get("token").asText();
                }

                private String createHealthCareProfAndGetId(String token) throws Exception {
                                String uniqueEmail = "hcp-" + UUID.randomUUID() + "@example.com";

                                MvcResult result = mockMvc.perform(post("/api/v1/healthcareprof/create")
                                                                .header("Authorization", "Bearer " + token)
                                                                .contentType(APPLICATION_JSON)
                                                                .content("""
                                                                                                {
                                                                                                        "name": "John",
                                                                                                        "surname": "Doe",
                                                                                                        "email": "%s",
                                                                                                        "specialties": ["CARD"]
                                                                                                }
                                                                                                """.formatted(uniqueEmail)))
                                                .andExpect(status().isCreated())
                                                .andReturn();

                                JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());
                                return response.get("id").asText();
                }

                private String createMedicalSalesRepAndGetId(String token) throws Exception {
                                String uniqueEmail = "msr-" + UUID.randomUUID() + "@example.com";

                                MvcResult result = mockMvc.perform(post("/api/v1/medicalsalesrep/create")
                                                                .header("Authorization", "Bearer " + token)
                                                                .contentType(APPLICATION_JSON)
                                                                .content("""
                                                                                                {
                                                                                                        "name": "Jane",
                                                                                                        "surname": "Smith",
                                                                                                        "email": "%s"
                                                                                                }
                                                                                                """.formatted(uniqueEmail)))
                                                .andExpect(status().isCreated())
                                                .andReturn();

                                JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());
                                return response.get("id").asText();
                }

                private void activateHealthCareProf(String token, String healthCareProfId) throws Exception {
                                mockMvc.perform(post("/api/v1/healthcareprof/activate")
                                                                .header("Authorization", "Bearer " + token)
                                                                .contentType(APPLICATION_JSON)
                                                                .content("""
                                                                                                {
                                                                                                        "id": "%s"
                                                                                                }
                                                                                                """.formatted(healthCareProfId)))
                                                .andExpect(status().isOk());
                }

                private void activateMedicalSalesRep(String token, String medicalSalesRepId) throws Exception {
                                mockMvc.perform(post("/api/v1/medicalsalesrep/activate")
                                                                .header("Authorization", "Bearer " + token)
                                                                .contentType(APPLICATION_JSON)
                                                                .content("""
                                                                                                {
                                                                                                        "medicalSalesRepId": "%s"
                                                                                                }
                                                                                                """.formatted(medicalSalesRepId)))
                                                .andExpect(status().isOk());
                }
}
