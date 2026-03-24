package com.example.e2eapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e2eapp.domain.dto.DashboardEmailData
import com.example.e2eapp.domain.usecase.DashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewmodel
    @Inject
    constructor(
        private val dashboardUseCase: DashboardUseCase,
    ) : ViewModel() {
        private var _dashboardData = MutableStateFlow<List<DashboardEmailData>>(emptyList())
        val dashboardData = _dashboardData.asStateFlow()

        fun getDashboardDataFromCloud() {
            viewModelScope.launch {
                // Assuming your use case has a method to invoke or get data
                dashboardUseCase.getDashboardUseCase().collect {
                    _dashboardData.value = it
                }
            }
        }
    }
