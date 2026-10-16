
CREATE TABLE emails (
    id BIGSERIAL PRIMARY KEY,
    recipient_email VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    order_code VARCHAR(255) NOT NULL,
    body TEXT NOT NULL
);
