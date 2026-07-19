package com.example.e2eapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e2eapp.domain.EmailDetailsResult
import com.example.e2eapp.domain.dto.EmailDetailsData
import com.example.e2eapp.domain.usecase.EmailDetailsUseCase
import com.example.e2eapp.provideAPIKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EmailDetailsViewmodel
    @Inject
    constructor(
        var emailDetailsUseCase: EmailDetailsUseCase,
    ) : ViewModel() {
        private val _profilePhoto = MutableStateFlow<String>("")
        val profilePhoto = _profilePhoto.asStateFlow()

        // Start with an empty list instead of a dummy item
        private val _senderDetails = MutableStateFlow<List<EmailDetailsData>>(emptyList())
        val senderDetails = _senderDetails.asStateFlow()

        fun fetchSenderDP(id: Int) {
            viewModelScope.launch {
                emailDetailsUseCase.fetchSenderDP(id, provideAPIKey()).collect {
                    _profilePhoto.value = it
                }
            }
        }

        fun fetchSenderDetails() {
            viewModelScope.launch {
                emailDetailsUseCase.fetchSenderDetails().collect { event ->
                    when (event) {
                        is EmailDetailsResult.UserDetails -> {
                            // Create a NEW list to trigger StateFlow/Compose updates
                            val currentList = _senderDetails.value.toMutableList()
                            currentList.add(event.emailDetailsData)
                            _senderDetails.value = currentList
                        }

                        is EmailDetailsResult.UserDetailsError -> {
                            Log.d("Error-EmailDetailsViewmodel","")
                        }

                    }
                }
            }
        }
    }
