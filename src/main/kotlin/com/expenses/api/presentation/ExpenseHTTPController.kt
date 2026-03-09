package com.expenses.api.presentation

import com.expenses.api.domain.Expense
import com.expenses.api.presentation.dto.CreateExpenseRequest
import com.expenses.api.usecase.CreateExpenseUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ExpenseHTTPController(
    val createExpenseUseCase: CreateExpenseUseCase
) {

    companion object {
        const val EXPENSES_RESOURCE = "/v1/expenses"
    }

    @PostMapping(EXPENSES_RESOURCE)
    fun create(@RequestBody @Valid createExpenseRequest: CreateExpenseRequest) =
        createExpenseUseCase.create(createExpenseRequest.toDomain())
}