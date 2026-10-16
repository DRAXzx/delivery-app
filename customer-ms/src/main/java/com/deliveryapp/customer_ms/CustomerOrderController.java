
package com.deliveryapp.customer_ms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("/orders")
public class CustomerOrderController {

    private final RestClient sellerClient;

    public CustomerOrderController(
            @Value("${seller.url}") String sellerUrl) {

        this.sellerClient = RestClient.create(sellerUrl);
    }

    @PostMapping
    public ResponseEntity<String> criarPedido(
            @RequestBody String pedidoJson) {

        try {
            String resposta = sellerClient.post()
                    .uri("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(pedidoJson)
                    .retrieve()
                    .body(String.class);

            return ResponseEntity.ok(resposta);

        } catch (RestClientException e) {

            return ResponseEntity.status(502)
                    .body("Erro ao enviar pedido para seller-ms");
        }
    }
}
