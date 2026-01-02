package com.fotkurz.expensetrackerapi.api

import com.fotkurz.expensetrackerapi.api.impl.FindExpensesResponse


interface ExpenseController {

    fun getExpenses(): List<FindExpensesResponse>

    fun getExpense(id: Long): FindExpensesResponse
}