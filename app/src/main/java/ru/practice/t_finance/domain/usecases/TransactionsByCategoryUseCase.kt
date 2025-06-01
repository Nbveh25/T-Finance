package ru.practice.t_finance.domain.usecases

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.ui.graphics.Color
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Response
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.model.ExpensesGraph
import ru.practice.t_finance.domain.repository.ExpensesRepository
import javax.inject.Inject

class TransactionsByCategoryUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun invoke(startDate: String, endDate: String): List<Category> {
        Log.d("MyLog", "$startDate и $endDate")

        // Парсим даты
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val start = LocalDateTime.parse(startDate, formatter)
        val end = LocalDateTime.parse(endDate, formatter)

        return when {
            isDateRange(start, end, "2025-05-29", "2025-05-30") -> mockMay29to30()
            isDateRange(start, end, "2025-05-18", "2025-05-25") -> mockMay18to25()
            isDateRange(start, end, "2025-03-01", "2025-04-01") -> mockMarchToApril()
            else -> {
                throw HttpException(
                    "404", Throwable()
                )
            }
        }
    }

    private fun isDateRange(
        start: LocalDateTime,
        end: LocalDateTime,
        minDate: String,
        maxDate: String
    ): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val min = LocalDateTime.parse("$minDate 00:00:00", formatter)
        val max = LocalDateTime.parse("$maxDate 23:59:59", formatter)

        return !start.isBefore(min) && !end.isAfter(max)
    }

    private fun mockMay29to30(): List<Category> {
        return listOf(
            Category(name = "Машина", color = Color(0xFF0000FF), value = 35),
            Category(name = "Кафешки", color = Color(0xFF8000FF), value = 35),
            Category(name = "Кошка", color = Color(0xFFEAA114), value = 10),
            Category(name = "Пк", color = Color(0xFF00D2C9), value = 20)
        )
    }

    private fun mockMay18to25(): List<Category> {
        return listOf(
            Category(name = "Продукты", color = Color(0xFFFF00E5), value = 60),
            Category(name = "Развлечения", color = Color(0xFF4AEA02), value = 10),
            Category(name = "Кафе", color = Color(0xFFEAA114), value = 15),
            Category(name = "Транспорт", color = Color(0xFF2000D2), value = 15)
        )
    }

    private fun mockMarchToApril(): List<Category> {
        return listOf(
            Category(name = "Продукты", color = Color(0xFF0000FF), value = 50),
            Category(name = "Развлечения", color = Color(0xFFFF0000), value = 20),
            Category(name = "Кафе", color = Color(0xFFEAA114), value = 10),
            Category(name = "Транспорт", color = Color(0xFF00D2C9), value = 20)
        )
    }

    private fun mockDefault(): List<Category> {
        return listOf(
            Category(name = "Продукты", color = Color(0xFF00FF2A), value = 40),
            Category(name = "Развлечения", color = Color(0xFFFF6F00), value = 30),
            Category(name = "Кафе", color = Color(0xFFEAA114), value = 10),
            Category(name = "Транспорт", color = Color(0xFFD2007E), value = 20)
        )
    }
}