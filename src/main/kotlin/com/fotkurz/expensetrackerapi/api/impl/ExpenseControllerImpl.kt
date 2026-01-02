package com.fotkurz.expensetrackerapi.api.impl

import com.fotkurz.expensetrackerapi.api.ExpenseController
import com.fotkurz.expensetrackerapi.usecase.FindExpensesUsecase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(path = ["/v1/expenses"])
class ExpenseControllerImpl(
    private val findExpensesUsecase: FindExpensesUsecase
): ExpenseController {

    @GetMapping
    override fun getExpenses(): List<FindExpensesResponse> =
        findExpensesUsecase.findAll().map { FindExpensesResponse.fromDomain(it) }

    @GetMapping("/{id}")
    override fun getExpense(@PathVariable @Valid id: Long): FindExpensesResponse {
        val found = findExpensesUsecase.findOne(id)
        return FindExpensesResponse.fromDomain(found!!)
    }

}