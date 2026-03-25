package com.expenses.api.service

import com.expenses.api.domain.Expense
import com.expenses.api.presentation.dto.FindExpensesRequest
import com.expenses.api.repository.ExpenseRepository
import com.expenses.api.repository.entity.ExpenseEntity
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.util.Optional
import java.util.UUID
import kotlin.test.assertEquals


@SpringBootTest
class ExpenseServiceTest(
) {
    private lateinit var expenseRepository: ExpenseRepository
    private lateinit var expenseService: ExpenseService

    @BeforeEach
    fun setup() {
        expenseRepository = mockk()
        expenseService = ExpenseService(expenseRepository)
    }

    @AfterEach
    fun tearDown() = clearAllMocks()

    @Test
    fun `should find all expenses using default pagination`() {
        val expenses = listOf(
            ExpenseEntity(
                title = "title",
                createdAt = OffsetDateTime.now(),
                expendedAt = OffsetDateTime.now(),
                amount = 100.0,
                labels = listOf("label1", "label2"),
                id = UUID.randomUUID(),
                userId = UUID.randomUUID(),
            )
        )

        val req = PageRequest.of(0, 2).withSort(Sort.by(Sort.Direction.DESC, "createdAt"))

        every { expenseRepository.findAll(req) } returns PageImpl(
            expenses, req, 1
        )

        val result = expenseService.findAllPageable(FindExpensesRequest(pageNumber = 0, pageSize = 2))

        assertEquals(2, result.size)
        assertEquals(1, result.items.size)
        assertEquals(expenses[0].title, result.items[0].title)
        assertEquals(expenses[0].amount, result.items[0].amount)
        assertEquals(expenses[0].labels, result.items[0].labels)
        assertEquals(expenses[0].userId, result.items[0].userId)
        assertEquals(expenses[0].createdAt.toZonedDateTime(), result.items[0].createdAt)
        assertEquals(expenses[0].expendedAt.toZonedDateTime(), result.items[0].expendedAt)
        assertNull(result.items[0].updatedAt)
    }

    @Test
    fun `should find one expense by id`() {
        val id = UUID.randomUUID()
        val userId = UUID.fromString("a2cc64db-b745-4ee1-83e7-27fae887d1c6")
        val expense = ExpenseEntity(
            id = id,
            title = "title",
            createdAt = OffsetDateTime.now(),
            expendedAt = OffsetDateTime.now(),
            amount = 100.0,
            labels = listOf("label1", "label2"),
            userId = userId,
        )

        every { expenseRepository.findById(eq(id)) } returns Optional.of(expense)

        val expects = Expense(
            id = id,
            title = "title",
            createdAt = expense.createdAt.toZonedDateTime(),
            expendedAt = expense.expendedAt.toZonedDateTime(),
            amount = 100.0,
            labels = listOf("label1", "label2"),
            userId = userId,
        )

        val result = expenseService.findById(id)

        assertEquals(expects.id, result.id)
        assertEquals(expects.title, result.title)
        assertEquals(expects.amount, result.amount)
        assertEquals(expects.labels, result.labels)
        assertEquals(expects.userId, result.userId)
        assertEquals(expects.createdAt, result.createdAt)
        assertEquals(expects.expendedAt, result.expendedAt)
        assertNull(result.updatedAt)
    }

    @Test
    fun `should create a expense with success`() {
        val id = UUID.randomUUID()

        val new = Expense(
            title = "title",
            createdAt = ZonedDateTime.now(),
            expendedAt = ZonedDateTime.now(),
            amount = 100.0,
            labels = listOf("label1", "label2"),
        )

        every { expenseRepository.save(match { it.title == "title" && it.amount == 100.0 }) } answers {
            val exp = firstArg<ExpenseEntity>()
            exp.id = id
            exp
        }


        val result = expenseService.create(new)

        assertEquals(id, result)
    }
}