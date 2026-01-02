package com.fotkurz.expensetrackerapi.usecase

import com.fotkurz.expensetrackerapi.api.dto.CreateExpenseRequest
import com.fotkurz.expensetrackerapi.domain.Expense

interface CreateExpenseUsecase {

    fun create(createExpenseRequest: CreateExpenseRequest): Expense
}
