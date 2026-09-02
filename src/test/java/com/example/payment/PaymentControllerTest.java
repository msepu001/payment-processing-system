package com.example.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * PaymentControllerTest
 *
 * Purpose:
 * Tests the Payment REST API without requiring us to manually
 * send requests from requests.http.
 *
 * @SpringBootTest starts the Spring application context for the test.
 * @AutoConfigureMockMvc creates a MockMvc object that lets us simulate
 * HTTP requests such as POST /payments.
 *
 * MockMvc lets us verify things like:
 * - HTTP status codes (201, 400, 404)
 * - JSON response bodies
 * - Controller behavior
 */

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    // MockMvc allows us to simulate HTTP requests to our controller.
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreatePayment() throws Exception {

        String requestBody = """
                {
                  "id": "Pay-100",
                  "amount": 250.00,
                  "status": "COMPLETED"
                }
                """;

        mockMvc.perform(
                        post("/payments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("Pay-100"))
                .andExpect(jsonPath("$.amount").value(250.00))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    /*
     * These tests verify the GET /payments/{id} endpoint.
     *
     * The first test creates a payment through the API and then retrieves it.
     * We expect HTTP 200 and the correct payment JSON.
     *
     * The second test requests an ID that does not exist.
     * We expect HTTP 404.
     *
     * MockMvc simulates HTTP requests without needing to manually
     * use requests.http or a browser.
     */

    @Test
    void shouldFindPaymentById() throws Exception {

        // Arrange: create a payment first.
        String requestBody = """
            {
              "id": "Pay-200",
              "amount": 350.00,
              "status": "COMPLETED"
            }
            """;

        mockMvc.perform(
                post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                .andExpect(status().isCreated());

        // Act + Assert: retrieve the payment.
        mockMvc.perform(
                        get("/payments/Pay-200")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("Pay-200"))
                .andExpect(jsonPath("$.amount").value(350.00))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void shouldReturn404WhenPaymentDoesNotExist() throws Exception {

        // Request an ID that we know does not exist.
        mockMvc.perform(
                        get("/payments/Pay-999")
                )
                .andExpect(status().isNotFound());
    }

    /*
     * Tests validation for POST /payments.
     *
     * This sends a payment with a negative amount.
     * Because CreatePaymentRequest uses @Positive, Spring should reject
     * the request before it reaches the service layer.
     *
     * Expected result:
     * HTTP 400 Bad Request
     * JSON message explaining why validation failed.
     */

    @Test
    void shouldRejectNegativePaymentAmount() throws Exception {

        String requestBody = """
            {
              "id": "Pay-101",
              "amount": -50.00,
              "status": "PENDING"
            }
            """;

        mockMvc.perform(
                        post("/payments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message")
                                .value("Payment amount must be greater than zero")
                );
    }

    /*
     * Tests filtering payments by status.
     *
     * This test:
     * 1. Creates one COMPLETED payment.
     * 2. Creates one PENDING payment.
     * 3. Calls GET /payments?status=COMPLETED.
     * 4. Verifies that only the COMPLETED payment is returned.
     *
     * This proves that the controller accepts the query parameter
     * and that the repository filtering works through the full
     * Controller -> Service -> Repository chain.
     */

    @Test
    void shouldReturnOnlyCompletedPayments() throws Exception {

        String completedPayment = """
            {
              "id": "Pay-300",
              "amount": 400.00,
              "status": "COMPLETED"
            }
            """;

        String pendingPayment = """
            {
              "id": "Pay-301",
              "amount": 150.00,
              "status": "PENDING"
            }
            """;

        // Create a COMPLETED payment.
        mockMvc.perform(
                        post("/payments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(completedPayment)
                )
                .andExpect(status().isCreated());

        // Create a PENDING payment.
        mockMvc.perform(
                        post("/payments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(pendingPayment)
                )
                .andExpect(status().isCreated());

        // Request only COMPLETED payments.
        mockMvc.perform(
                        get("/payments")
                                .param("status", "COMPLETED")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == 'Pay-300')]").exists())
                .andExpect(jsonPath("$[?(@.id == 'Pay-301')]").doesNotExist());
    }

    /*
     * Tests updating an existing payment.
     *
     * Flow:
     * 1. Create a payment.
     * 2. Send PUT /payments/{id}.
     * 3. Verify HTTP 200.
     * 4. Verify the updated amount and status in the JSON response.
     */
    @Test
    void shouldUpdatePayment() throws Exception {

        String createRequest = """
            {
              "id": "Pay-400",
              "amount": 100.00,
              "status": "PENDING"
            }
            """;

        String updateRequest = """
            {
              "amount": 600.00,
              "status": "COMPLETED"
            }
            """;

        mockMvc.perform(
                        post("/payments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createRequest)
                )
                .andExpect(status().isCreated());

        mockMvc.perform(
                        put("/payments/Pay-400")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateRequest)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("Pay-400"))
                .andExpect(jsonPath("$.amount").value(600.00))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    /*
     * Tests deleting an existing payment.
     *
     * Flow:
     * 1. Create a payment.
     * 2. Delete it.
     * 3. Expect HTTP 204 No Content.
     * 4. Try to retrieve it afterward.
     * 5. Expect HTTP 404 Not Found.
     */
    @Test
    void shouldDeletePayment() throws Exception {

        String requestBody = """
            {
              "id": "Pay-500",
              "amount": 300.00,
              "status": "PENDING"
            }
            """;

        mockMvc.perform(
                        post("/payments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated());

        mockMvc.perform(
                        delete("/payments/Pay-500")
                )
                .andExpect(status().isNoContent());

        mockMvc.perform(
                        get("/payments/Pay-500")
                )
                .andExpect(status().isNotFound());
    }
}
