
package com.deliveryapp.emailms.service;

import com.deliveryapp.emailms.dto.OrderNotificationDTO;
import com.deliveryapp.emailms.entity.Email;
import com.deliveryapp.emailms.repository.EmailRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;

    @Value("${app.mail.from:delivery-app@email.com}")
    private String remetente;

    public EmailService(EmailRepository emailRepository,
                        JavaMailSender mailSender) {
        this.emailRepository = emailRepository;
        this.mailSender = mailSender;
    }

    public void processarNotificacao(OrderNotificationDTO pedido) {

        String assunto = "Recebemos seu pedido #" + pedido.orderCode();

        String corpo = montarCorpo(pedido);

        Email email = new Email(
                pedido.to(),
                assunto,
                pedido.orderCode(),
                corpo
        );

        emailRepository.save(email);

        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setFrom(remetente);
        mensagem.setTo(pedido.to());
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);

        mailSender.send(mensagem);

        System.out.println(
                "Email enviado para: " + pedido.to()
        );
    }

    private String montarCorpo(OrderNotificationDTO pedido) {

        StringBuilder texto = new StringBuilder();

        texto.append("Olá, ")
                .append(pedido.customerName())
                .append("!\n\n");

        texto.append("Seu pedido foi recebido pelo estabelecimento ")
                .append(pedido.establishmentName())
                .append(".\n\n");

        texto.append("Itens do pedido:\n");

        if (pedido.items() != null) {
            for (OrderNotificationDTO.ItemDTO item : pedido.items()) {

                texto.append("- ")
                        .append(item.name())
                        .append(" (x")
                        .append(item.quantity())
                        .append(") - R$ ")
                        .append(formatarValor(item.subtotal()))
                        .append("\n");
            }
        }

        texto.append("\nTotal do pedido: R$ ")
                .append(formatarValor(pedido.total()));

        return texto.toString();
    }

    private String formatarValor(BigDecimal valor) {

        return valor.setScale(2, RoundingMode.HALF_UP)
                .toPlainString();
    }
}
