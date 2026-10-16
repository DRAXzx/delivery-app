
package com.deliveryapp.emailms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_email", nullable = false)
    private String to;

    @Column(nullable = false)
    private String subject;

    @Column(name = "order_code", nullable = false)
    private String orderCode;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    public Email() {
    }

    public Email(String to, String subject,
                 String orderCode, String body) {
        this.to = to;
        this.subject = subject;
        this.orderCode = orderCode;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
