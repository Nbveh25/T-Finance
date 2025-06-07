package ru.practice.t_finance.presentation.screens.main

sealed class MainScreenState {
    object Initial: MainScreenState()
    object Loading: MainScreenState()
    object Success: MainScreenState()
    data class Error(val message: String): MainScreenState()

}