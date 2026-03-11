package com.expenses.api.repository

import com.expenses.api.repository.entity.ExpenseEntity
import org.springframework.context.annotation.Bean
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ExpenseRepository : CrudRepository<ExpenseEntity, UUID> {

}