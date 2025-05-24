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
public class BestBuysPerUnitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testBestBuysPerUnitControllerEndpoint() throws Exception {
        String requestJson = """       
                {
                "date": "2025-05-09"
                }                
                """;


        mockMvc.perform(post("/rest/best-deals/on-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ouă mărimea M"))
                .andExpect(jsonPath("$[0].brand").value("Din Ogradă"))
                .andExpect(jsonPath("$[0].store").value("profi"))
                .andExpect(jsonPath("$[0].packageQuantity").value(10.0))
                .andExpect(jsonPath("$[0].packageUnit").value("buc"))
                .andExpect(jsonPath("$[0].valuePerUnit").value(1.125))
                .andExpect(jsonPath("$[0].currency").value("RON"))
                .andExpect(jsonPath("$[0].final_price").value(11.25))

                .andExpect(jsonPath("$[1].name").value("ouă mărimea M"))
                .andExpect(jsonPath("$[1].brand").value("Ferma Veche"))
                .andExpect(jsonPath("$[1].store").value("kaufland"))
                .andExpect(jsonPath("$[1].packageQuantity").value(10.0))
                .andExpect(jsonPath("$[1].packageUnit").value("buc"))
                .andExpect(jsonPath("$[1].valuePerUnit").value(1.2104))
                .andExpect(jsonPath("$[1].currency").value("RON"))
                .andExpect(jsonPath("$[1].final_price").value(12.1))

                .andExpect(jsonPath("$[2].name").value("ouă mărimea M"))
                .andExpect(jsonPath("$[2].brand").value("Lidl"))
                .andExpect(jsonPath("$[2].store").value("profi"))
                .andExpect(jsonPath("$[2].packageQuantity").value(10.0))
                .andExpect(jsonPath("$[2].packageUnit").value("buc"))
                .andExpect(jsonPath("$[2].valuePerUnit").value(1.22))
                .andExpect(jsonPath("$[2].currency").value("RON"))
                .andExpect(jsonPath("$[2].final_price").value(12.2))

                .andExpect(jsonPath("$[3].name").value("ouă mărimea M"))
                .andExpect(jsonPath("$[3].brand").value("Lidl"))
                .andExpect(jsonPath("$[3].store").value("profi"))
                .andExpect(jsonPath("$[3].packageQuantity").value(10.0))
                .andExpect(jsonPath("$[3].packageUnit").value("buc"))
                .andExpect(jsonPath("$[3].valuePerUnit").value(1.22))
                .andExpect(jsonPath("$[3].currency").value("RON"))
                .andExpect(jsonPath("$[3].final_price").value(12.2))
        ;

    }

}



