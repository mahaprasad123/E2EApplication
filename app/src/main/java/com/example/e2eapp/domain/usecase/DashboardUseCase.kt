package com.example.e2eapp.domain.usecase

import com.example.e2eapp.domain.dto.DashboardEmailData
import com.example.e2eapp.domain.repo.DashboardRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DashboardUseCase @Inject constructor(private val repo: DashboardRepo) {
    suspend fun getDashboardUseCase(): Flow<List<DashboardEmailData>> {
        return repo.getDashboardDetails()
    }
}