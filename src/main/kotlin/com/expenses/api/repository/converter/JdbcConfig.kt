package com.expenses.api.repository.converter

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions

@Configuration
class JdbcConfig {
    @Bean
    fun jdbcCustomConversions(): JdbcCustomConversions =
        JdbcCustomConversions(
            listOf(
                OffsetDateTimeConverter(),
                TimestampConverter()
            )
        )
}