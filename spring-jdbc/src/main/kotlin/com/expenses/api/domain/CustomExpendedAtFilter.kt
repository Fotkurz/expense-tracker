package com.expenses.api.domain

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CustomExpendedAtFilter {

    companion object {
        private const val DATETIME_FORMATTER = "yyyy-MM-dd HH:mm:ss"
        private const val CUSTOM_EXPENDED_AT_SPLITTER = "@"
    }

    var from: ZonedDateTime = ZonedDateTime.parse("1900-01-01T00:00:00Z")
        private set

    var to: ZonedDateTime = ZonedDateTime.parse("3000-01-01T00:00:00Z")
        private set

    constructor(expendedAt: String) {
        val parts = expendedAt.split(CUSTOM_EXPENDED_AT_SPLITTER)
        if (parts.isNotEmpty()) { // if contains _, can be a range or from first to timestamp
            val pattern = DateTimeFormatter.ofPattern(DATETIME_FORMATTER)

            if (parts.size > 1) {
                if (parts[0].isNotBlank()) { // from@to
                    from = ZonedDateTime.parse(parts[0], pattern)
                    to = ZonedDateTime.parse(parts[1], pattern)
                } else { // only @to
                    to = ZonedDateTime.parse(parts[1], pattern)
                }
                // TODO: maybe only from should still contain @ like from@
            } else { // only from
                from = ZonedDateTime.parse(parts[0], pattern)
            }
            throw IllegalArgumentException("expendedAt requires a timestamp")
        }
    }
}