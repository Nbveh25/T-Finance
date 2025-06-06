package ru.practice.t_finance.data.repository

import android.util.Log
import androidx.compose.ui.graphics.Color
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.model.ExpensesGraph
import ru.practice.t_finance.domain.repository.ExpensesRepository
import javax.inject.Inject
import kotlin.math.roundToInt

class ExpensesRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ExpensesRepository {
    override suspend fun getExpenses(startDate: String, endDate: String): Result<ExpensesGraph> {
        return try {
            when (val response = api.getExpenses(startDate, endDate)) {
                is NetworkResponse.Success -> {
                    val data = response.data
                    val graphData = ExpensesGraph(
                        categories = data.categories.map { item ->
                            Category(
                                id = item.category.id.toInt(),
                                name = item.category.name,
                                color = item.category.color.toComposeColor(),
                                value = item.percentage.roundToInt()
                            )
                        },
                        amount = data.amount.toDouble().roundToInt()
                    )

                    Result.success(graphData)
                }

                is NetworkResponse.EmptySuccess -> {
                    Result.success(ExpensesGraph(emptyList(), 0))
                }

                is NetworkResponse.ApiError -> {
                    val message = when (response.code) {
                        400 -> {
                            "Неверный запрос"
                        }
                        401 -> "Неавторизованный пользователь"
                        else -> {
                            "Ошибка API: ${response.code}"
                        }
                    }
                    Log.d("ExpensesRepository", message)
                    Result.failure(Exception(message))
                }

                is NetworkResponse.NetworkError -> {
                    Result.failure(Exception(response.error))
                }

                is NetworkResponse.UnknownError -> {
                    Log.d("ExpensesRepository", "Unknown error", response.error)
                    Result.failure(Exception("Неизвестная ошибка в графе"))
                }
            }
        } catch (e: Exception) {
            Log.e("ExpensesRepository", "Unexpected error", e)
            Result.failure(e)
        }
    }


}

fun String.toComposeColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}