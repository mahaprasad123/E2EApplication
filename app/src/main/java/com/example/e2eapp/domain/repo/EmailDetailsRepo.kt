package com.example.e2eapp.domain.repo

import com.example.e2eapp.domain.EmailDetailsResult
import kotlinx.coroutines.flow.Flow

interface EmailDetailsRepo {
    suspend fun fetchSenderDetails(): Flow<EmailDetailsResult>

    suspend fun fetchSenderDP(
        id: Int,
        token: String,
    ): Flow<String>
}
