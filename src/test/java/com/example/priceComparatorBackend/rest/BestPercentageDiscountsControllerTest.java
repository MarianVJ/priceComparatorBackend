package com.example.priceComparatorBackend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BestPercentageDiscountsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    public BestPercentageDiscountsControllerTest(MockMvc mockMvc) {

        this.mockMvc = mockMvc;
    }

    @Test
    public void testBestPercentageDiscountsControllerEndpoint() throws Exception {
        String requestJson = """       
                {
                "date": "2025-05-09"
                }                
                """;


        mockMvc.perform(post("/rest/best-discounts/on-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("spaghetti nr.5"))
                .andExpect(jsonPath("$[0].discount").value(20.0))
                .andExpect(jsonPath("$[0].brand").value("Barilla"))
                .andExpect(jsonPath("$[0].store").value("lidl"))
                .andExpect(jsonPath("$[0].currency").value("RON"))
                .andExpect(jsonPath("$[0].price_without_discount").value(5.7))
                .andExpect(jsonPath("$[1].name").value("detergent lichid"))
                .andExpect(jsonPath("$[1].discount").value(20.0))
                .andExpect(jsonPath("$[1].brand").value("Persil"))
                .andExpect(jsonPath("$[1].store").value("lidl"))
                .andExpect(jsonPath("$[1].currency").value("RON"))
                .andExpect(jsonPath("$[1].price_without_discount").value(49.5))
                .andExpect(jsonPath("$[2].name").value("spaghetti nr.5"))
                .andExpect(jsonPath("$[2].discount").value(18.0))
                .andExpect(jsonPath("$[2].brand").value("Barilla"))
                .andExpect(jsonPath("$[2].store").value("kaufland"))
                .andExpect(jsonPath("$[2].currency").value("RON"))
                .andExpect(jsonPath("$[2].price_without_discount").value(5.85))
                .andExpect(jsonPath("$[3].name").value("detergent lichid"))
                .andExpect(jsonPath("$[3].discount").value(18.0))
                .andExpect(jsonPath("$[3].brand").value("Dero"))
                .andExpect(jsonPath("$[3].store").value("profi"))
                .andExpect(jsonPath("$[3].currency").value("RON"))
                .andExpect(jsonPath("$[3].price_without_discount").value(48.9))
                .andExpect(jsonPath("$[4].name").value("ciocolată neagră 70%"))
                .andExpect(jsonPath("$[4].discount").value(15.0))
                .andExpect(jsonPath("$[4].brand").value("Heidi"))
                .andExpect(jsonPath("$[4].store").value("kaufland"))
                .andExpect(jsonPath("$[4].currency").value("RON"))
                .andExpect(jsonPath("$[4].price_without_discount").value(4.15))

        ;

    }

}



