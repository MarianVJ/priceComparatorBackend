package com.example.priceComparatorBackend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DynamicPriceHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetDynamicPriceHistoryRepositoryByCategoryEndpoit() throws Exception {


        mockMvc.perform(get("/rest/dynamic-price-history/category/lactate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("brânză telemea"))
                .andExpect(jsonPath("$[0].productBrand").value("Hochland"))
                .andExpect(jsonPath("$[0].store").value("kaufland"))
                .andExpect(jsonPath("$[0].dataPoints[0].date").value("2025-05-01"))
                .andExpect(jsonPath("$[0].dataPoints[0].price").value(11.79))
                .andExpect(jsonPath("$[0].dataPoints[1].date").value("2025-05-08"))
                .andExpect(jsonPath("$[0].dataPoints[1].price").value(13.0))

                // 2nd product
                .andExpect(jsonPath("$[1].productName").value("brânză telemea"))
                .andExpect(jsonPath("$[1].productBrand").value("Pilos"))
                .andExpect(jsonPath("$[1].store").value("lidl"))
                .andExpect(jsonPath("$[1].dataPoints[0].date").value("2025-05-01"))
                .andExpect(jsonPath("$[1].dataPoints[0].price").value(10.88))
                .andExpect(jsonPath("$[1].dataPoints[1].date").value("2025-05-08"))
                .andExpect(jsonPath("$[1].dataPoints[1].price").value(11.61))
                .andExpect(jsonPath("$[1].dataPoints[2].date").value("2025-05-15"))
                .andExpect(jsonPath("$[1].dataPoints[2].price").value(12.9))

                // 3rd product
                .andExpect(jsonPath("$[2].productName").value("brânză telemea"))
                .andExpect(jsonPath("$[2].productBrand").value("Pilos"))
                .andExpect(jsonPath("$[2].store").value("profi"))
                .andExpect(jsonPath("$[2].dataPoints[0].date").value("2025-05-01"))
                .andExpect(jsonPath("$[2].dataPoints[0].price").value(12.8));
        ;

    }


    @Test
    public void testGetDynamicPriceHistoryRepositoryByBrandEndpoit() throws Exception {


        mockMvc.perform(get("/rest/dynamic-price-history/brand/Pilos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("brânză telemea"))
                .andExpect(jsonPath("$[0].productCategory").value("lactate"))
                .andExpect(jsonPath("$[0].store").value("lidl"))
                .andExpect(jsonPath("$[0].dataPoints[0].date").value("2025-05-01"))
                .andExpect(jsonPath("$[0].dataPoints[0].price").value(10.88))
                .andExpect(jsonPath("$[0].dataPoints[1].date").value("2025-05-08"))
                .andExpect(jsonPath("$[0].dataPoints[1].price").value(11.61))
                .andExpect(jsonPath("$[0].dataPoints[2].date").value("2025-05-15"))
                .andExpect(jsonPath("$[0].dataPoints[2].price").value(12.9))

                // 3rd product
                .andExpect(jsonPath("$[1].productName").value("brânză telemea"))
                .andExpect(jsonPath("$[1].productCategory").value("lactate"))
                .andExpect(jsonPath("$[1].store").value("profi"))
                .andExpect(jsonPath("$[1].dataPoints[0].date").value("2025-05-01"))
                .andExpect(jsonPath("$[1].dataPoints[0].price").value(12.8));

    }

}



