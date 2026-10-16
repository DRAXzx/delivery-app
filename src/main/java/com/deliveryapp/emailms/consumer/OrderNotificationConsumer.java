
package com.deliveryapp.emailms.consumer;

import com.deliveryapp.emailms.dto.OrderNotificationDTO;
import com.deliveryapp.emailms.service.EmailService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderNotificationConsumer {

    private final EmailService emailService;

    public OrderNotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "order-notification",
            groupId = "email-ms-group"
    )
    public void consumir(OrderNotificationDTO pedido) {

        System.out.println(
                "Pedido recebido pelo Kafka: " + pedido.orderCode()
        );

        emailService.processarNotificacao(pedido);
    }
}
