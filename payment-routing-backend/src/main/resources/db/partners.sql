CREATE TYPE direction_type AS ENUM ('INBOUND', 'OUTBOUND');
CREATE TYPE processed_flow_type AS ENUM ('MESSAGE', 'ALERTING', 'NOTIFICATION');

CREATE TABLE partners (
                          id SERIAL PRIMARY KEY,
                          alias VARCHAR(255) NOT NULL UNIQUE,
                          type VARCHAR(255) NOT NULL,
                          direction direction_type,
                          application VARCHAR(255),
                          processed_flow_type processed_flow_type,
                          description TEXT NOT NULL
);


----------------


INSERT INTO partners (alias, type, direction, application, processed_flow_type, description) VALUES
                (
                'BANK_TRANS_PARTNER',
                'FINANCIAL_INSTITUTION',
                'INBOUND',
                'BANK_TRANSACTION_APP',
                'MESSAGE',
                'Partenaire principal pour les transactions bancaires'
                ),
                (
                'PAYMENT_GATEWAY',
                'PAYMENT_PROCESSOR',
                'OUTBOUND',
                'PAYMENT_SYSTEM',
                'ALERTING',
                'Passerelle de paiement externe'
                ),
                (
                'CUSTOMER_NOTIFY',
                'COMMUNICATION_SERVICE',
                'INBOUND',
                'NOTIFICATION_ENGINE',
                'NOTIFICATION',
                'Service de notifications clients'
                ),
                (
                'FRAUD_DETECTION',
                'SECURITY_SYSTEM',
                'INBOUND',
                'RISK_MANAGEMENT_APP',
                'ALERTING',
                'Système de détection des fraudes'
                ),
                (
                'ACCOUNTING_SYNC',
                'FINANCIAL_REPORTING',
                'OUTBOUND',
                'ACCOUNTING_SOFTWARE',
                'MESSAGE',
                'Synchronisation avec le système comptable'
                );