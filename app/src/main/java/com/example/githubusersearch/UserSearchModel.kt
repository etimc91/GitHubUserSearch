package com.example.githubusersearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusersearch.core.Result
import com.example.githubusersearch.domain.usecase.GetUserUseCase
import com.example.githubusersearch.presentation.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    private val _inputState = MutableStateFlow("")
    val inputState: StateFlow<String> = _inputState.asStateFlow()

    fun updateSearchInput(input: String) {
        _inputState.value = input
    }

    fun searchUser() {
        val query = inputState.value
        if (query.isBlank()) {
            _searchState.value = SearchState(error = "Search input cannot be empty")
            return
        }

        getUserUseCase.invoke(inputState.value)
            .onEach { result ->
                _searchState.value = when (result) {
                    is Result.Loading -> _searchState.value.copy(isLoading = true)
                    is Result.Success -> _searchState.value.copy(user = result.data, isLoading = false)
                    is Result.Error -> _searchState.value.copy(error = result.message, isLoading = false)
                }
            }
            .launchIn(viewModelScope)
    }
}