package ru.practice.t_finance.domain.usecases

import ru.practice.t_finance.domain.model.TransactionModel
import ru.practice.t_finance.domain.repository.ExpensesRepository
import javax.inject.Inject

class ExpensesUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    fun invoke(startDate: String, endDate: String) : List<TransactionModel>{
        val sampleTransactions = listOf(
            TransactionModel(
                name = "Покупка в магазине",
                category = "Продукты",
                amount = 450.50,
                date = "2025-06-01 10:30:00",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a5/Instagram_icon.png"
            ),
            TransactionModel(
                name = "Кофе в кафе",
                category = "Кафе",
                amount = 90.00,
                date = "2025-06-01 15:45:00",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a5/Instagram_icon.png"
            ),
            TransactionModel(
                name = "Проезд в метро",
                category = "Транспорт",
                amount = 60.00,
                date = "2025-06-02 08:15:00",
                imageUrl = null
            ),
            TransactionModel(
                name = "Фильм в кинотеатре",
                category = "Развлечения",
                amount = 550.00,
                date = "2025-06-03 20:00:00",
                imageUrl = "https://example.com/images/cinema.png"
            ),
            TransactionModel(
                name = "Обед в ресторане",
                category = "Кафе",
                amount = 700.00,
                date = "2025-06-04 13:00:00",
                imageUrl = "https://example.com/images/restaurant.png"
            ),
            TransactionModel(
                name = "Покупка фруктов",
                category = "Продукты",
                amount = 300.00,
                date = "2025-06-05 09:20:00",
                imageUrl = "https://example.com/images/fruits.png"
            )
        )
        return sampleTransactions
    }
}