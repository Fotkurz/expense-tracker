package com.fotkurz.expensetrackerapi.persistence.entity

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "expenses")
class ExpenseEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @field:Column(nullable = false, unique = false, length = 25)
    var title: String,
    @field:Column(nullable = true, unique = false, length = 255)
    var description: String? = null,
    @field:Column(nullable = false, unique = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    @field:Column(nullable = false, unique = false)
    var expendedAt : ZonedDateTime = ZonedDateTime.now(),
    @field:Column(nullable = false, unique = false)
    var amount: Double,
)