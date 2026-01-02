package com.fotkurz.expensetrackerapi.persistence.entity

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "expenses")
class Expense(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @field:Column(nullable = false, unique = false, length = 25)
    val title: String,
    @field:Column(nullable = true, unique = false, length = 255)
    val description: String? = null,
    @field:Column(nullable = false, unique = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    @field:Column(nullable = false, unique = false)
    val expendedAt : ZonedDateTime = ZonedDateTime.now(),
    @field:Column(nullable = false, unique = false)
    val amount: Double,
)