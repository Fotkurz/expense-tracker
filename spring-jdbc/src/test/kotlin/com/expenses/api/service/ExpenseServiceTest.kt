package com.expenses.api.service

import com.expenses.api.domain.Expense
import com.expenses.api.domain.exception.ResourceNotFoundException
import com.expenses.api.presentation.dto.FindExpensesRequest
import com.expenses.api.repository.ExpenseRepository
import com.expenses.api.repository.entity.ExpenseEntity
import io.mockk.MockK
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle
import org.assertj.core.data.Offset
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.assertThrows
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
    private val testUserId = UUID.fromString("a2cc64db-b745-4ee1-83e7-27fae887d1c6")

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
        val userId = testUserId
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
    fun `should threw ResourceNotFoundException if not found by id`() {
        val id = UUID.randomUUID()
        val userId = testUserId
        val expense = ExpenseEntity(
            id = id,
            title = "title",
            createdAt = OffsetDateTime.now(),
            expendedAt = OffsetDateTime.now(),
            amount = 100.0,
            labels = listOf("label1", "label2"),
            userId = userId,
        )

        every { expenseRepository.findById(eq(id)) } returns Optional.empty()

        val exception = assertThrows<ResourceNotFoundException> {
            expenseService.findById(id)
        }

        assertEquals("resource with id <$id> not found", exception.message)
    }

    @Test
    fun `should create a expense and return its id`() {
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

    @Test
    fun `should delete by id`() {
        val id = UUID.randomUUID()
        every { expenseRepository.deleteById(id) } returns Unit
        assertDoesNotThrow { expenseRepository.deleteById(id) }
    }

    @Test
    fun `should update expense by id`() {
        val id = UUID.randomUUID()
        val newTitle = "title"
        val amount = 100.0
        val labels = listOf("label1", "label2")
        val expendedAt = ZonedDateTime.now()

        val old = ExpenseEntity(
            id = id,
            title = "old",
            userId = testUserId,
            createdAt = OffsetDateTime.now().minusWeeks(1),
            expendedAt = OffsetDateTime.now().minusWeeks(1),
            updatedAt = null,
            amount = 50.0,
            labels = listOf(),
        )

        val new = ExpenseEntity(
            id, newTitle, amount, labels, expendedAt.toOffsetDateTime(), old.createdAt, mockkClass(
                OffsetDateTime::class
            ), testUserId
        )

        every { expenseRepository.findById(id) } returns Optional.of(old)
        every {
            expenseRepository.save(match {
                it.id == id && it.title == newTitle && it.amount == amount && it.expendedAt == OffsetDateTime.from(expendedAt) && it.labels == labels
            })
        } returns new

        val result = expenseService.update(id, newTitle, amount, labels, expendedAt)

        assertEquals(id, result.id)
        assertEquals(newTitle, result.title)
        assertEquals(amount, result.amount)
        assertEquals(labels, result.labels)
        assertEquals(testUserId, result.userId)
        assertEquals(expendedAt.toInstant(), result.expendedAt.toInstant())
        assertNotNull(result.updatedAt)
    }
}