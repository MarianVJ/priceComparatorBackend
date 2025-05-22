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
public class BasketOptimizerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testBasketOptimizerEndpoint() throws Exception {
        String requestJson = """
                
                  {
                    "date": "2025-05-13",
                    "products": [
                      "lapte zuzu",
                      "cafea măcinată",
                      "iaurt grecesc",
                      "apa de izvor"
                    ]
                  }
                
                """;


        mockMvc.perform(post("/rest/basket-optimizer/optimize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendedShopping.lidl").isArray())
                .andExpect(jsonPath("$.recommendedShopping.profi").isArray())
                .andExpect(jsonPath("$.storeTotals.lidl").value(27.83))
                .andExpect(jsonPath("$.storeTotals.profi").value(10.37))
                .andExpect(jsonPath("$.basketTotal").value(38.199999999999996))
                .andExpect(jsonPath("$.notes[0]").value(
                        "Product not found: apa de izvor"));
    }

    @Test
    public void testEmptyProductListReturnsValidEmptyResponse()
            throws Exception {
        String requestJson = """
                    {
                      "date": "2025-05-13",
                      "products": []
                    }
                """;

        mockMvc.perform(post("/rest/basket-optimizer/optimize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendedShopping").isEmpty())
                .andExpect(jsonPath("$.storeTotals").isEmpty())
                .andExpect(jsonPath("$.basketTotal").value(0.0))
                .andExpect(jsonPath("$.notes").isEmpty());
    }

    @Test
    public void testAllProductsNotFound() throws Exception {
        String requestJson = """
                    {
                      "date": "2025-05-13",
                      "products": ["produs inexistent 1", "altceva necunoscut"]
                    }
                """;

        mockMvc.perform(post("/rest/basket-optimizer/optimize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendedShopping").isEmpty())
                .andExpect(jsonPath("$.storeTotals").isEmpty())
                .andExpect(jsonPath("$.basketTotal").value(0.0))
                .andExpect(jsonPath("$.notes[0]").value(
                        "Product not found: produs inexistent 1"))
                .andExpect(jsonPath("$.notes[1]").value(
                        "Product not found: altceva necunoscut"));
    }

    @Test
    public void testMissingDateField() throws Exception {
        String requestJson = """
                    {
                      "products": ["lapte zuzu"]
                    }
                """;

        mockMvc.perform(post("/rest/basket-optimizer/optimize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes[0]").value("date is missing from" +
                        " the request"));
    }


}



