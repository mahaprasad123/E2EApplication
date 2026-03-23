package com.example.e2eapp.domain.repo


import com.example.e2eapp.domain.dto.DashboardEmailData
import kotlinx.coroutines.flow.Flow

interface DashboardRepo {
    suspend fun getDashboardDetails(): Flow<List<DashboardEmailData>>
}