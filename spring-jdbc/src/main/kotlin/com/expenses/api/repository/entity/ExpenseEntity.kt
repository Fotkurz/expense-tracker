package com.expenses.api.repository.entity

import com.expenses.api.domain.Expense
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.UUID

/*
TODO:
If you're using:

TIMESTAMP WITHOUT TIME ZONE → prefer LocalDateTime or Instant

TIMESTAMP WITH TIME ZONE → OffsetDateTime is usually safer than ZonedDateTime
 */

@Table("expenses")
class ExpenseEntity(
    @field:Id
    @field:Column("id")
    var id: UUID? = null,

    @field:Column("title")
    @field:Size(min = 1, max = 55)
    var title: String,

    @field:NotNull
    @field:Column("amount")
    var amount: Double,
    @field:Column("labels")
    var labels: List<String>,

    @field:NotNull
    @field:Column("expended_at")
    var expendedAt: OffsetDateTime,

    @field:NotNull
    @field:Column("created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    @field:Column("updated_at")
    var updatedAt: OffsetDateTime? = null,

    @field:Column("user_id")
    val userId: UUID,
    ) {

    fun toDomain(): Expense =
        Expense(
            id = id,
            userId = userId,
            title = title,
            amount = amount,
            labels = labels,
            expendedAt = expendedAt.toZonedDateTime(),
            createdAt = createdAt.toZonedDateTime(),
            updatedAt = updatedAt?.toZonedDateTime(),
        )

}