package com.example.e2eapp.domain.usecase

import com.example.e2eapp.domain.EmailDetailsResult
import com.example.e2eapp.domain.repo.EmailDetailsRepo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class EmailDetailsUseCase
    @Inject
    constructor(
        var repo: EmailDetailsRepo,
    ) {
        suspend fun fetchSenderDetails(): Flow<EmailDetailsResult> = repo.fetchSenderDetails()

        suspend fun fetchSenderDP(
            id: Int,
            token: String,
        ): Flow<String> = repo.fetchSenderDP(id, token)
    }
