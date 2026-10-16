
# Delivery App - Microsserviços

Atividade de Programação para Internet.

## Tecnologias utilizadas

- Java 25
- Spring Boot 4.1.1
- Apache Kafka
- PostgreSQL
- Flyway
- Docker e Docker Compose
- MailHog

## Microsserviços

- customer-ms: recebe pedidos dos clientes.
- seller-ms: verifica se o pedido está ACCEPTED.
- email-ms: consome notificações do Kafka,
  salva no PostgreSQL e envia e-mails.

## Execução

Na pasta principal, executar:

docker compose up --build -d

## Endereços

Customer: http://localhost:8081

Seller: http://localhost:8082

Email: http://localhost:8083

MailHog: http://localhost:8025

## Funcionamento

1. O customer-ms recebe o pedido.
2. O seller-ms verifica o status.
3. Se ACCEPTED, publica no Kafka.
4. O email-ms consome a notificação.
5. O email-ms salva os dados no PostgreSQL.
6. O email é enviado ao MailHog.

Pedidos com status diferente de ACCEPTED
não geram notificações.
