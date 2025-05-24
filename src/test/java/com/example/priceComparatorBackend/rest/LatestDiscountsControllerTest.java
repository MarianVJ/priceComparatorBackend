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
public class LatestDiscountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLatestDiscountsControllerEndpoint() throws Exception {
        String requestJson = """       
                {
                "date": "2025-05-09"
                }                
                """;


        mockMvc.perform(post("/rest/latest-discounts/on-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("lapte zuzu"))
                .andExpect(jsonPath("$[0].discount").value(9.0))
                .andExpect(jsonPath("$[0].brand").value("Zuzu"))
                .andExpect(jsonPath("$[0].store").value("kaufland"))
                .andExpect(jsonPath("$[0].currency").value("RON"))
                .andExpect(jsonPath("$[0].price_without_discount").value(10.0))
                .andExpect(jsonPath("$[1].name").value("ouă mărimea M"))
                .andExpect(jsonPath("$[1].discount").value(11.0))
                .andExpect(jsonPath("$[1].brand").value("Ferma Veche"))
                .andExpect(jsonPath("$[1].store").value("kaufland"))
                .andExpect(jsonPath("$[1].currency").value("RON"))
                .andExpect(jsonPath("$[1].price_without_discount").value(13.6))
                .andExpect(jsonPath("$[2].name").value("ulei floarea-soarelui"))
                .andExpect(jsonPath("$[2].discount").value(8.0))
                .andExpect(jsonPath("$[2].brand").value("Floriol"))
                .andExpect(jsonPath("$[2].store").value("kaufland"))
                .andExpect(jsonPath("$[2].currency").value("RON"))
                .andExpect(jsonPath("$[2].price_without_discount").value(9.6))
                .andExpect(jsonPath("$[3].name").value("hârtie igienică 3 straturi"))
                .andExpect(jsonPath("$[3].discount").value(13.0))
                .andExpect(jsonPath("$[3].brand").value("Pufina"))
                .andExpect(jsonPath("$[3].store").value("kaufland"))
                .andExpect(jsonPath("$[3].currency").value("RON"))
                .andExpect(jsonPath("$[3].price_without_discount").value(19.3))
        ;

    }

}



