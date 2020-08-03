package com.nikhiljain.fundoonotes.data

import com.nikhiljain.fundoonotes.data.converters.TypeConverters
import org.junit.Assert
import org.junit.Test
import java.util.*

class TypeConvertersTest {

    @Test
    fun shouldConvertDateToFormattedString() {
        val date = Date(1596446724699)
        val formattedDateString =
            TypeConverters.fromOffsetDateTime(date)
        val expectedString = "2020-08-03T14:55:24.699Z"
        Assert.assertEquals(expectedString, formattedDateString)
    }

    @Test
    fun shouldConvertProperStringToDate() {
        val formattedString = "2020-08-03T14:55:24.699Z"
        val convertedDate =
            TypeConverters.toOffsetDateTime(formattedString)
        val expectedDate = Date(1596446724699)
        Assert.assertEquals(expectedDate, convertedDate)
    }

    @Test
    fun shouldNotConvertFaultyStringToDate() {
        val formattedString = "2020-08-03T14:55"
        val convertedDate =
            TypeConverters.toOffsetDateTime(formattedString)
        Assert.assertNull(convertedDate)
    }
}