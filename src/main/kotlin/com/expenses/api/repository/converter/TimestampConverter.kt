package com.expenses.api.repository.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Component
@WritingConverter
class TimestampConverter: Converter<Timestamp, OffsetDateTime> {
    override fun convert(source: Timestamp): OffsetDateTime =
        source.toInstant().atOffset(ZoneOffset.UTC)
}

