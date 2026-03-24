package com.example.e2eapp.data.network

import com.example.e2eapp.data.dto.DashboardMasterData
import retrofit2.http.GET

interface IDashboardNetwork {
    @GET("emaillist")
    suspend fun getDashboardDataFromCloud(): List<DashboardMasterData>
}
