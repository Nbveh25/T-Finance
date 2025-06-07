package ru.practice.t_finance.domain.usecases.main

import jakarta.inject.Inject
import ru.practice.t_finance.domain.repository.TransactionRepository
import java.text.SimpleDateFormat
import java.util.Date

class GetTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke() = transactionRepository.getTransactionsListByDate(startDate = "2025-01-01 00:00:00", endDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))
}