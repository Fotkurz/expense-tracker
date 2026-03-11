package com.expenses.api.repository.entity

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.ZonedDateTime
import java.util.UUID

@Table("expenses")
class ExpenseEntity(
    @field:Id
    @field:Column("id")
    var id: UUID? = null,

    @field:Column("title")
    @field:Size(min = 1, max = 55)
    val title: String,

    @field:NotNull
    @field:Column("amount")
    val amount: Double,
    @field:Column("labels")
    val labels: List<String>,

    @field:NotNull
    @field:Column("expended_at")
    val expendedAt: ZonedDateTime,

    @field:NotNull
    @field:Column("created_at")
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    @field:Column("updated_at")
    val updatedAt: ZonedDateTime? = null,

    @field:Column("user_id")
    val userId: UUID = UUID.fromString("a2cc64db-b745-4ee1-83e7-27fae887d1c6"),
    )