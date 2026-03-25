package com.expenses.api.domain

data class Page<T>(
    val size: Int,
    val page: Int,
    val items: List<T>,
)