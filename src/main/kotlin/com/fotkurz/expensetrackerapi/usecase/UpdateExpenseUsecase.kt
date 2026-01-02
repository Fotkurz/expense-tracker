package com.fotkurz.expensetrackerapi.usecase

import com.fotkurz.expensetrackerapi.api.dto.UpdateExpenseRequest
import com.fotkurz.expensetrackerapi.domain.Expense

interface UpdateExpenseUsecase {

    fun update(id: Long, updateExpenseRequest: UpdateExpenseRequest): Expense
}
