package com.fotkurz.expensetrackerapi.persistence

import com.fotkurz.expensetrackerapi.persistence.entity.Expense
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExpenseJpaRepository: JpaRepository<Expense, Long> {
}