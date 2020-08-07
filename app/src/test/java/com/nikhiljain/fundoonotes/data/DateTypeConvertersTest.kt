package com.nikhiljain.fundoonotes.data

import org.junit.Assert
import org.junit.Test
import java.util.*

class DateTypeConvertersTest {
    private val dateTypeConverters = DateTypeConverters()

    @Test
    fun shouldConvertDateToFormattedString() {
        val date = Date(1596446724699)
        val formattedDateString =
            dateTypeConverters.fromOffsetDateTime(date)
        val expectedString = "2020-08-03T14:55:24.699Z"
        Assert.assertEquals(expectedString, formattedDateString)
    }

    @Test
    fun shouldConvertProperStringToDate() {
        val formattedString = "2020-08-03T14:55:24.699Z"
        val convertedDate =
            dateTypeConverters.toOffsetDateTime(formattedString)
        val expectedDate = Date(1596446724699)
        Assert.assertEquals(expectedDate, convertedDate)
    }

    @Test
    fun shouldNotConvertFaultyStringToDate() {
        val formattedString = "2020-08-03T14:55"
        val convertedDate =
            dateTypeConverters.toOffsetDateTime(formattedString)
        Assert.assertNull(convertedDate)
    }
}