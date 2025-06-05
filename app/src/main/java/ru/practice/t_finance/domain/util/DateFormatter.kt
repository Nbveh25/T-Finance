package ru.practice.t_finance.domain.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {
    @RequiresApi(Build.VERSION_CODES.O)
    private val inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    @RequiresApi(Build.VERSION_CODES.O)
    private val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @RequiresApi(Build.VERSION_CODES.O)
    fun format(dateString: String): String {
        val parsedDate = LocalDate.parse(dateString, inputFormatter)
        return parsedDate.format(outputFormatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun reverseFormat(dateString: String): String {
        val parsedDate = LocalDate.parse(dateString, outputFormatter)
        return parsedDate.format(inputFormatter)
    }
}