package ru.practice.t_finance.presentation.screens.expenses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.usecases.expenses.GetTransactionsListByDateUseCase
import ru.practice.t_finance.domain.usecases.expenses.TransactionsByCategoryUseCase
import ru.practice.t_finance.presentation.model.TransactionListItem
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getTransactionsUseCase: TransactionsByCategoryUseCase,
    private val getExpenseUseCase: GetTransactionsListByDateUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpensesUiState>(ExpensesUiState.Loading)
    val uiState: StateFlow<ExpensesUiState> = _uiState

//    private var _period = mutableStateOf<CurrentPeriod>(CurrentPeriod(PeriodType.MONTH,deriveMonthRange()))
//    val period: State<CurrentPeriod> = _period

    private val _startDate = MutableStateFlow(startOfMonth())
    private val _endDate = MutableStateFlow(nowDate())

    val startDate: StateFlow<String> = _startDate
    val endDate: StateFlow<String> = _endDate

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")


    private val _periodType = MutableStateFlow(PeriodType.DAY)
    val periodType: StateFlow<PeriodType> = _periodType




    fun shiftPeriod(offset: Int) {
        viewModelScope.launch {
            val start = LocalDateTime.parse(_startDate.value, formatter)
            val end = LocalDateTime.parse(_endDate.value, formatter)

            val daysDiff = ChronoUnit.DAYS.between(start, end).toInt()

            val newStart = when (periodType.value) {
                PeriodType.DAY -> start.plusDays(offset.toLong())
                PeriodType.WEEK -> start.plusWeeks(offset.toLong())
                PeriodType.MONTH -> start.plusMonths(offset.toLong())
                PeriodType.YEAR -> start.plusYears(offset.toLong())
            }

            val newEnd = newStart.plusDays(daysDiff.toLong())

            _startDate.emit(newStart.format(formatter))
            _endDate.emit(newEnd.format(formatter))

            loadData(newStart.format(formatter), newEnd.format(formatter))
        }
    }

    fun loadData(startDate: String, endDate: String){
        Log.d("MyLog", "$startDate $endDate аавыа")
        viewModelScope.launch {
            var parsedStart = LocalDateTime.parse(startDate, formatter).with(LocalTime.MIN)
            var parsedEnd = LocalDateTime.parse(endDate, formatter).with ( LocalTime.MAX )
            val now = LocalDateTime.now()

            // Если endDate больше текущей даты — корректируем его
            if (parsedEnd.isAfter(now)) {
                parsedEnd = now
                Log.d("MyLog", "Конечная дата скорректирована до текущей: $now")
            }
            _startDate.emit(parsedStart.format(formatter))
            _endDate.emit(parsedEnd.format(formatter))
            val start = LocalDateTime.parse(_startDate.value, formatter)
            val end = LocalDateTime.parse(_endDate.value, formatter)

            val daysDiff = ChronoUnit.DAYS.between(start, end).toInt()
            changePeriodTypeByDiff(daysDiff)
            Log.d("MyLog", _periodType.value.toString())
            runCatching {
                val transactionsResult = getTransactionsUseCase.invoke(_startDate.value,_endDate.value)
                val expensesResult = getExpenseUseCase.invoke(_startDate.value,_endDate.value).getOrThrow()
                val dataTransactions = transactionsResult.getOrThrow()

                val transactionsList = expensesResult.map {
                    TransactionListItem(
                        name = it.name,
                        category = it.category,
                        imageUrl = it.imageUrl,
                        amountFormatted = it.amount.toString()
                    )
                }
                _uiState.emit(
                    ExpensesUiState.Success(
                        categories = dataTransactions.categories,
                        totalExpenses = dataTransactions.amount,
                        transactions = transactionsList
                    )
                )
            }.onFailure { message ->
                _uiState.value = ExpensesUiState.Error(
                    message = message.message ?: "Ошибка"
                )
            }
        }
    }

    fun changePeriodTypeByDiff(dif: Int){
        viewModelScope.launch {
            viewModelScope.launch {
                _periodType.emit(
                    when (dif) {
                        in 0..1 -> PeriodType.DAY
                        in 2..7 -> PeriodType.WEEK
                        in 8..30 -> PeriodType.MONTH
                        else -> PeriodType.YEAR
                    }
                )
            }
        }
    }

    fun changePeriodType(newType: Int){
        viewModelScope.launch {
            when (newType) {
                0 -> {
                    _endDate.emit(nowDate())
                    val newStart = LocalDateTime.now().minusDays(1)
                    _startDate.emit(newStart.format(formatter))
                    _periodType.emit(PeriodType.DAY)
                }
                1 -> {
                    _endDate.emit(nowDate())
                    val newStart = LocalDateTime.now().minusWeeks(1)
                    _startDate.emit(newStart.format(formatter))
                    _periodType.emit(PeriodType.WEEK)
                }
                2 -> {
                    _endDate.emit(nowDate())
                    val newStart = LocalDateTime.now().minusMonths(1)
                    _startDate.emit(newStart.format(formatter))
                    _periodType.emit(PeriodType.MONTH)
                }
                3 -> {
                    _endDate.emit(nowDate())
                    val newStart = LocalDateTime.now().minusYears(1)
                    _startDate.emit(newStart.format(formatter))
                    _periodType.emit(PeriodType.YEAR)
                }
            }



            loadData(_startDate.value,_endDate.value)
        }
    }

    fun nowDate() : String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        return now.format(formatter)
    }

    fun startOfMonth() : String {
        val now = LocalDateTime.now()
        val startOfMonth = now.withDayOfMonth(1).with(LocalTime.MIN)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return startOfMonth.format(formatter)
    }


    fun formatPeriodForDisplay(startDate: String, endDate: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val start = LocalDateTime.parse(startDate, formatter)
        val end = LocalDateTime.parse(endDate, formatter)

        return when {
            isSameDay(start, end) -> {
                // Один день
                start.format(DateTimeFormatter.ofPattern("d MMMM", Locale.getDefault()))
            }
            isSameMonth(start, end) -> {
                // Тот же месяц
                "${start.dayOfMonth}–${end.dayOfMonth} ${start.format(DateTimeFormatter.ofPattern("LLLL", Locale.getDefault()))}"
            }
            isSameYear(start, end) -> {
                // Тот же год, разные месяцы
                "${start.dayOfMonth} ${start.format(DateTimeFormatter.ofPattern("LLL"))} – " +
                        "${end.dayOfMonth} ${end.format(DateTimeFormatter.ofPattern("LLL"))}"
            }
            else -> {
                // Разные годы
                "${start.format(DateTimeFormatter.ofPattern("d MMM"))} – " +
                        "${end.format(DateTimeFormatter.ofPattern("d MMM"))}"
            }
        }
    }

    private fun isSameDay(a: LocalDateTime, b: LocalDateTime): Boolean =
        a.year == b.year && a.month == b.month && a.dayOfMonth == b.dayOfMonth

    private fun isSameMonth(a: LocalDateTime, b: LocalDateTime): Boolean =
        a.year == b.year && a.month == b.month

    private fun isSameYear(a: LocalDateTime, b: LocalDateTime): Boolean =
        a.year == b.year

}



sealed class ExpensesUiState{
    object Loading : ExpensesUiState()
    data class Success(
        val categories: List<Category>,
        val totalExpenses: Int,
        val transactions: List<TransactionListItem>
    ) : ExpensesUiState()
    data class Error(val message: String) : ExpensesUiState()
}