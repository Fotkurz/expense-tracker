package com.expenses.api.presentation

import com.expenses.api.presentation.dto.CreateExpenseRequest
import com.expenses.api.presentation.dto.ExpenseResponse
import com.expenses.api.presentation.dto.FindExpensesRequest
import com.expenses.api.presentation.dto.PaginatedExpenseResponse
import com.expenses.api.presentation.dto.UpdateExpenseRequest
import com.expenses.api.service.ExpenseService
import jakarta.validation.Valid
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID

@RestController
@RequestMapping(value = ["/v1/expenses"])
class ExpenseHTTPController(
    val expenseService: ExpenseService
) {

    @GetMapping
    fun findAll(
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sortBy", defaultValue = "createdAt") sortBy: String,
        @RequestParam("expendedAt") expendedAt: String? = null,
    ): PaginatedExpenseResponse {
        val expensePage = expenseService.findAllPageable(FindExpensesRequest(
            pageNumber = page, pageSize = size, sort = Sort.by(sortBy), expendedAt = expendedAt,
        ))
        return PaginatedExpenseResponse(
            size = expensePage.size,
            page = page,
            items = expensePage.items.map { ExpenseResponse.fromDomain(it) }
        )
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid createExpenseRequest: CreateExpenseRequest): ResponseEntity<Map<String, String>> {
        val id = expenseService.create(createExpenseRequest.toDomain()).toString()

        return ResponseEntity.created(ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri()).body(mapOf("id" to id))
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ExpenseResponse? = ExpenseResponse.fromDomain(expenseService.findById(id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = expenseService.delete(id)


    @PutMapping("/{id}")
    fun delete(@PathVariable id: UUID, @RequestBody @Valid updateExpenseRequest: UpdateExpenseRequest) = expenseService.update(id, updateExpenseRequest.title, updateExpenseRequest.amount, updateExpenseRequest.labels, updateExpenseRequest.expendedAt)
}