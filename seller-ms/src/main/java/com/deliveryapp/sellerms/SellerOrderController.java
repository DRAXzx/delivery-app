
package com.deliveryapp.sellerms;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class SellerOrderController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SellerOrderController(
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<String> receberPedido(
            @RequestBody Pedido pedido) {

        if (!"ACCEPTED".equals(pedido.status())) {

            return ResponseEntity.ok(
                    "Pedido nao aceito. Nenhum email sera enviado."
            );
        }

        Notificacao notificacao = new Notificacao(
                pedido.to(),
                pedido.customerName(),
                pedido.establishmentName(),
                pedido.orderCode(),
                pedido.items(),
                pedido.total()
        );

        kafkaTemplate.send(
                "order-notification",
                pedido.orderCode(),
                notificacao
        );

        System.out.println(
                "Pedido aceito e publicado no Kafka: "
                        + pedido.orderCode()
        );

        return ResponseEntity.ok(
                "Pedido aceito. Notificacao enviada ao Kafka."
        );
    }

    public record Pedido(
            String status,
            String to,
            String customerName,
            String establishmentName,
            String orderCode,
            List<Item> items,
            BigDecimal total
    ) {
    }

    public record Item(
            String name,
            int quantity,
            BigDecimal subtotal
    ) {
    }

    public record Notificacao(
            String to,
            String customerName,
            String establishmentName,
            String orderCode,
            List<Item> items,
            BigDecimal total
    ) {
    }
}
