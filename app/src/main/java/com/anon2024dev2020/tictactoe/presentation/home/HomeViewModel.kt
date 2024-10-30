package com.anon2024dev2020.tictactoe.presentation.home

import com.anon2024dev2020.tictactoe.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() :
    BaseViewModel<HomeViewModel.UiState, HomeViewModel.UiEvent>() {

    init {
        mutableUiState.value = UiState.Success("Successfully loaded this message :)")
    }

    override fun initialState(): UiState = UiState.Initial

    override fun onEvent(event: UiEvent) {
    }

    sealed class UiEvent : BaseUiEvent

    sealed class UiState : BaseUiState {
        data object Initial : UiState()
        data object Loading : UiState()
        data class Success(val screenName: String = "Home Screen") : UiState()
        data class Error(val message: String) : UiState()
    }
}
