erDiagram

    addresses {
        bigint id PK
        varchar between_street
        varchar city
        varchar external_number
        varchar internal_number
        varchar latitude
        varchar longitude
        varchar municipality
        varchar neighborhood
        varchar postal_code
        varchar request_latitude
        varchar request_longitude
        varchar residence_country
        varchar residence_state
        boolean status
        varchar street
        timestamp created_at
        timestamp updated_at
    }

    board_of_directors {
        bigint id PK
        varchar name
        varchar position
        boolean status
        timestamp created_at
        timestamp updated_at
        bigint id_corporate_client FK
    }

    clients {
        bigint id PK
        varchar client_type
        timestamp created_at
        boolean status
        timestamp updated_at
        bigint id_instance
        bigint id_user
        bigint id_address FK
    }

    controlling_entities {
        bigint id PK
        varchar name
        boolean status
        timestamp created_at
        timestamp updated_at
        bigint id_corporate_client FK
    }

    corporate_clients {
        bigint id PK
        varchar business_activity
        varchar email
        varchar incorporation_date
        varchar name
        varchar number_of_employees
        varchar phone_one
        varchar phone_two
        varchar rfc
        varchar serial_number
        boolean status
        varchar subtype
        timestamp created_at
        timestamp updated_at
        bigint id_client FK
        bigint id_legal_representative FK
    }

    corporate_structure {
        bigint id PK
        varchar name
        varchar position
        boolean status
        timestamp created_at
        timestamp updated_at
        bigint id_corporate_client FK
    }

    guarantees {
        bigint id PK
        varchar name
        boolean status
        timestamp created_at
        timestamp updated_at
        bigint id_client FK
    }

    individual_clients {
        bigint id PK
        varchar marital_status
        varchar ocupation
        boolean status
        varchar subtype
        timestamp created_at
        timestamp updated_at
        bigint id_client FK
        bigint id_person FK
    }

    joint_obligors {
        bigint id PK
        timestamp created_at
        boolean status
        timestamp updated_at
        bigint id_user
        bigint id_address FK
        bigint id_client FK
        bigint id_person FK
    }

    persons {
        bigint id PK
        date birth_date
        varchar birth_state
        varchar curp
        varchar email
        varchar gender
        varchar last_name
        varchar maiden_name
        varchar name
        varchar nationality
        varchar phone_one
        varchar phone_two
        varchar rfc
        boolean status
        timestamp created_at
        timestamp updated_at
    }

    referencess {
        bigint id PK
        timestamp contract_date
        varchar executive
        varchar institution_name
        varchar name
        varchar phone_number
        varchar reference_type
        varchar relationship
        boolean status
        timestamp created_at
        timestamp updated_at
        bigint id_client FK
    }

    related_pep {
        bigint id PK
        varchar position
        varchar relationship
        boolean status
        timestamp created_at
        timestamp updated_at
        bigint id_individual_client FK
    }

    shareholders {
        bigint id PK
        varchar name
        float ownership_percentage
        boolean status
        timestamp created_at
        timestamp updated_at
        bigint id_corporate_client FK
    }

Relaciones entre tablas
board_of_directors ||--o| corporate_clients: "id_corporate_client"
clients ||--o| addresses: "id_address"
controlling_entities ||--o| corporate_clients: "id_corporate_client"
corporate_clients ||--o| individual_clients: "id_legal_representative"
corporate_clients ||--o| clients: "id_client"
corporate_structure ||--o| corporate_clients: "id_corporate_client"
guarantees ||--o| clients: "id_client"
individual_clients ||--o| clients: "id_client"
individual_clients ||--o| persons: "id_person"
joint_obligors ||--o| persons: "id_person"
joint_obligors ||--o| addresses: "id_address"
joint_obligors ||--o| clients: "id_client"
referencess ||--o| clients: "id_client"
related_pep ||--o| individual_clients: "id_individual_client"
shareholders ||--o| corporate_clients: "id_corporate_client"
