package com.expenses.api.repository.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.OffsetDateTime

@Component
@ReadingConverter
@WritingConverter
class OffsetDateTimeConverter: Converter<OffsetDateTime, Timestamp> {
    override fun convert(source: OffsetDateTime): Timestamp =
        Timestamp.from(source.toInstant())
}

