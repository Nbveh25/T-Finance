package ru.practice.t_finance.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practice.t_finance.domain.usecases.main.GetTransactionsUseCase
import javax.inject.Inject

@HiltViewModel
class MainVewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Initial)
    internal val state: StateFlow<MainScreenState> = _state.asStateFlow()

    fun getTransactions() {
        viewModelScope.launch {
            _state.value = MainScreenState.Loading

            getTransactionsUseCase.invoke().onSuccess {

            }.onFailure {

            }

        }
    }

}

