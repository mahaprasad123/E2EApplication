package com.example.e2eapp.domain.usecase

import com.example.e2eapp.domain.dto.DashboardEmailData
import com.example.e2eapp.domain.repo.DashboardRepo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class DashboardUseCase
    @Inject
    constructor(
        private val repo: DashboardRepo,
    ) {
        suspend fun getDashboardUseCase(): Flow<List<DashboardEmailData>> = repo.getDashboardDetails()
    }
