package com.expenses.api.presentation

import com.expenses.api.presentation.dto.CreateExpenseRequest
import com.expenses.api.repository.entity.ExpenseEntity
import com.expenses.api.usecase.CreateExpenseUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.Optional
import java.util.UUID

@RestController
@RequestMapping(value = ["/v1/expenses"])
class ExpenseHTTPController(
    val createExpenseUseCase: CreateExpenseUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid createExpenseRequest: CreateExpenseRequest): ResponseEntity<Map<String, String>> {
        val id = createExpenseUseCase.create(createExpenseRequest.toDomain()).toString()


        return ResponseEntity.created(ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri()).body(mapOf("id" to id))
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): Optional<ExpenseEntity> = createExpenseUseCase.findById(id)
}