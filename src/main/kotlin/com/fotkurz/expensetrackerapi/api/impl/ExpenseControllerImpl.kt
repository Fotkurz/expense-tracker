package com.fotkurz.expensetrackerapi.api.impl

import com.fotkurz.expensetrackerapi.api.ExpenseController
import com.fotkurz.expensetrackerapi.api.dto.CreateExpenseRequest
import com.fotkurz.expensetrackerapi.api.dto.ExpenseResponse
import com.fotkurz.expensetrackerapi.api.dto.UpdateExpenseRequest
import com.fotkurz.expensetrackerapi.usecase.CreateExpenseUsecase
import com.fotkurz.expensetrackerapi.usecase.DeleteExpenseUsecase
import com.fotkurz.expensetrackerapi.usecase.FindExpensesUsecase
import com.fotkurz.expensetrackerapi.usecase.UpdateExpenseUsecase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(path = ["/v1/expenses"])
class ExpenseControllerImpl(
    private val findExpensesUsecase: FindExpensesUsecase,
    private val createExpenseUsecase: CreateExpenseUsecase,
    private val updateExpenseUsecase: UpdateExpenseUsecase,
    private val deleteExpenseUsecase: DeleteExpenseUsecase
): ExpenseController {

    @GetMapping
    override fun get(): List<ExpenseResponse> =
        findExpensesUsecase.findAll().map { ExpenseResponse.fromDomain(it) }

    @PostMapping
    override fun create(@RequestBody @Valid model: CreateExpenseRequest): ExpenseResponse
         = ExpenseResponse.fromDomain(createExpenseUsecase.create(model))

    @GetMapping("/{id}")
    override fun getOne(@PathVariable @Valid id: Long): ExpenseResponse
            = ExpenseResponse.fromDomain(findExpensesUsecase.findOne(id)!!)

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable @Valid id: Long) =
        deleteExpenseUsecase.delete(id)

    @PutMapping("/{id}")
    override fun update(@PathVariable @Valid id: Long, @RequestBody @Valid model: UpdateExpenseRequest)
        = ExpenseResponse.fromDomain(updateExpenseUsecase.update(id, model))

}