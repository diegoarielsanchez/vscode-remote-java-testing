package com.das.cleanddd.application.health_check;


import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultMatcher;

final class HealthCheckGetControllerShould {
    @Autowired
	private MockMvc mockMvc;

    @Test
    void check_health_check_is_working_ok() 
        throws Exception {
            assertResponse("/health-check", 200, "{'application':'.','status':'ok'}");
        }
    private void assertResponse(String endpoint, Integer expectedStatusCode, String expectedResponse) throws Exception {
		ResultMatcher response = expectedResponse.isEmpty() ? content().string("") : content().json(expectedResponse);

		mockMvc.perform(get(endpoint)).andExpect(status().is(expectedStatusCode)).andExpect(response);
	}
}
