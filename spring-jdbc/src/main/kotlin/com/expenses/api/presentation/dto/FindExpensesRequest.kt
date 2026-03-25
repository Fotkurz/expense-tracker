package com.expenses.api.presentation.dto

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class FindExpensesRequest(
    val expendedAt: String? = null,
    pageNumber: Int,
    pageSize: Int,
    sort: Sort = Sort.by(Sort.Order.desc("createdAt"))
): PageRequest(pageNumber, pageSize, sort)
