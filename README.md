# Expense Tracking API

Project done following the premisses described in [this](https://roadmap.sh/projects/expense-tracker-api) exercise.

## Specs

- Kotlin 2.22 (Java 21) 
- Spring Boot 4.0.3
- Gradle 

## Data Model

**Entities**:
- User: An Expense API User
- Expense: Register of an amount expended/received by a user.

```mermaid
---
title: Entity Relationship Diagram
---
erDiagram
    User ||--o{ Expense: expend
    
    Expense {
        uuid id PK
        User user FK
        varchar(55) title
        double amount
        text[] labels
        timestampz expended_at "When it was expended"
        timestampz created_at "When the register was created"
        timestampz updated_at
    }
    
    User {
        uuid id PK
        varchar(100) username
        varchar(50) password
        varchar(100) firstname
        varchar(100) lastname
        timestampz created_at
        timestampz updated_at
    }
```

## TODOS

- [ ] Add db conn
- [ ] Add docker-compose
- [ ] Proper repository
- [ ] Hibernate and JPA
- [ ] Proper http controller error handling
- [ ] Authentication and Authorization


