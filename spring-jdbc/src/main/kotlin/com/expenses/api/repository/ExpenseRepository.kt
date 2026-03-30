package com.expenses.api.repository

import com.expenses.api.repository.entity.ExpenseEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.util.UUID

@Repository
interface ExpenseRepository : PagingAndSortingRepository<ExpenseEntity, UUID>,
    CrudRepository<ExpenseEntity, UUID> {

    fun findByExpendedAtBetween(startDate: ZonedDateTime?, endDate: ZonedDateTime, pageable: Pageable): Page<ExpenseEntity>

}