package com.expenses.api.repository

import com.expenses.api.repository.entity.ExpenseEntity
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ExpenseIdGenerator: BeforeConvertCallback<ExpenseEntity> {
    override fun onBeforeConvert(e: ExpenseEntity): ExpenseEntity {
        if (e.id == null) {
            e.id = UUID.randomUUID()
        }

        return e
    }
}