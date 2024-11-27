CREATE TABLE messages (
                          id SERIAL PRIMARY KEY,
                          content TEXT,
                          queue_name VARCHAR(255),
                          received_at TIMESTAMP WITHOUT TIME ZONE
);



---------------------



INSERT INTO messages (content, queue_name, received_at) VALUES
            (
                '{"transactionId": "TR001", "amount": 500.50, "customer": "John Doe"}',
                'TRANSACTION_QUEUE',
                CURRENT_TIMESTAMP
            ),
            (
                '{"transactionId": "TR002", "amount": 1200.75, "customer": "Jane Smith"}',
                'TRANSACTION_QUEUE',
                CURRENT_TIMESTAMP - INTERVAL '1 hour'
            ),
            (
                '{"transactionId": "TR003", "amount": 350.25, "customer": "Bob Johnson"}',
                'PAYMENT_QUEUE',
                CURRENT_TIMESTAMP - INTERVAL '2 hours'
            ),
            (
                '{"transactionId": "TR004", "amount": 900.00, "customer": "Alice Williams"}',
                'REFUND_QUEUE',
                CURRENT_TIMESTAMP - INTERVAL '3 hours'
            ),
            (
                '{"transactionId": "TR005", "amount": 250.50, "customer": "Charlie Brown"}',
                'NOTIFICATION_QUEUE',
                CURRENT_TIMESTAMP - INTERVAL '4 hours'
            );