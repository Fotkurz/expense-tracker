package com.expenses.api.service

import com.expenses.api.domain.ExpendedAtFilter
import com.expenses.api.domain.Expense
import com.expenses.api.domain.Page
import com.expenses.api.domain.exception.ResourceNotFoundException
import com.expenses.api.presentation.dto.FindExpensesRequest
import com.expenses.api.repository.ExpenseRepository
import com.expenses.api.repository.entity.ExpenseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class ExpenseService(
    val repository: ExpenseRepository,
) {

    fun findAllPageable(request: FindExpensesRequest): Page<Expense> {
        if (request.expendedAt != null) {
            return findAllFilteredByExpendedAt(request)
        }

        val paginated = repository.findAll(request)
        return Page(
            size = paginated.size,
            page = request.pageNumber,
            items = paginated.content.map { it.toDomain() },
        )
    }

    private fun findAllFilteredByExpendedAt(request: FindExpensesRequest): Page<Expense> {
        val expendedAt = request.expendedAt!!
        try {
            val filter = ExpendedAtFilter.valueOf(expendedAt)
            val paginated = when (filter) {
                ExpendedAtFilter.PAST_WEEK -> repository.findByExpendedAtBetween(LocalDate.now().minusWeeks(1),
                    LocalDate.now(), request)
                ExpendedAtFilter.PAST_MONTH -> repository.findAll(request)
                ExpendedAtFilter.PAST_3_MONTH -> repository.findAll(request)
            }

            return Page(
                size = paginated.size,
                page = request.pageNumber,
                items = paginated.content.map { it.toDomain() },
            )
        } catch (_: IllegalArgumentException) {
            // TODO: not yet working
            val haveSplitter = expendedAt.indexOf("_")
            if (haveSplitter != -1) { // if contains _, can be a range or from first to timestamp
                val parts = expendedAt.split("_")
                if (parts.size > 1) {
                    val from = LocalDate.parse(parts[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    val to = LocalDate.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    val found = repository.findByExpendedAtBetween(from, to, request)
                    return Page(
                        size = request.pageSize,
                        page = request.pageNumber,
                        items = found.content.map { it.toDomain() },
                    )
                }
                // TODO: filter from x to y
                // TODO: filter from first to x
                // TODO: exception if only contains '_' character
            } else { // does not contains _ so its from date until today
                // TODO: filter from x until today
            }
        }
        return Page(5, 5, listOf())
    }

    fun findById(id: UUID): Expense {
        val possible = repository.findById(id)
        if (!possible.isPresent) {
            throw ResourceNotFoundException(resourceId = id.toString())
        }

        return possible.get().toDomain()
    }

    @Transactional
    fun create(expense: Expense): UUID {

        try {
            val saved = repository.save(ExpenseEntity(
                title = expense.title,
                amount = expense.amount,
                labels = expense.labels!!,
                expendedAt = expense.expendedAt.toOffsetDateTime(),
                userId = expense.userId!!,
                createdAt = expense.createdAt.toOffsetDateTime()
            ))

            return saved.id!!
        } catch (ex: Exception) {
            // TODO: add more contextualized exception
            throw ex
        }
    }

    @Transactional
    fun delete(id: UUID) {
        repository.deleteById(id)
    }

    @Transactional
    fun update(id: UUID, title: String?, amount: Double?, labels: List<String>?, expendedAt: ZonedDateTime?): Expense {
        val optional = repository.findById(id)
        if (!optional.isPresent) {
            throw IllegalArgumentException("Expense with id $id does not exist")
        }

        val expense = optional.get()

        expense.title = title ?: expense.title
        expense.labels = labels ?: expense.labels
        expense.amount = amount ?: expense.amount
        expense.expendedAt = OffsetDateTime.from(expendedAt) ?: OffsetDateTime.from(expense.expendedAt)

        expense.updatedAt = OffsetDateTime.now()

        repository.save(expense)
        return expense.toDomain()
    }
}