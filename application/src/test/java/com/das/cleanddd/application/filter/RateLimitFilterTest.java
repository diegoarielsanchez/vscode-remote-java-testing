package com.das.cleanddd.application.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

class RateLimitFilterTest {

    @Test
    void shouldApplyRateLimitPerClientIp() throws Exception {
        Bandwidth bandwidth = Bandwidth.classic(2, Refill.intervally(100, Duration.ofMinutes(1)));
        RateLimitFilter filter = new RateLimitFilter(bandwidth);

        MockHttpServletResponse response1 = executeRequest(filter, "1.1.1.1");
        MockHttpServletResponse response2 = executeRequest(filter, "1.1.1.1");
        MockHttpServletResponse response3 = executeRequest(filter, "1.1.1.1");
        MockHttpServletResponse responseOtherClient = executeRequest(filter, "2.2.2.2");

        assertEquals(200, response1.getStatus());
        assertEquals(200, response2.getStatus());
        assertEquals(429, response3.getStatus());
        assertEquals(200, responseOtherClient.getStatus());
    }

    private MockHttpServletResponse executeRequest(RateLimitFilter filter, String ip) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/visit/list");
        request.setRemoteAddr(ip);
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new MockFilterChain());
        return response;
    }
}
