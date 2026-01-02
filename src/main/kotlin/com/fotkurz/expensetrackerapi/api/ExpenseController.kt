package com.fotkurz.expensetrackerapi.api

import com.fotkurz.expensetrackerapi.api.dto.CreateExpenseRequest
import com.fotkurz.expensetrackerapi.api.dto.ExpenseResponse
import com.fotkurz.expensetrackerapi.api.dto.UpdateExpenseRequest


interface ExpenseController {

    fun get(): List<ExpenseResponse>

    fun getOne(id: Long): ExpenseResponse

    fun create(model: CreateExpenseRequest): ExpenseResponse

    fun delete(id: Long)

    fun update(id: Long, model: UpdateExpenseRequest): ExpenseResponse
}