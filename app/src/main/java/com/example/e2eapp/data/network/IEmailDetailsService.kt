package com.example.e2eapp.data.network

import com.example.e2eapp.data.dto.UserAvtarData
import com.example.e2eapp.data.dto.UserDetailsData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface IEmailDetailsService {
    @GET("continents")
    suspend fun getUserDetailsFromCloud(): List<UserDetailsData>

    @GET("users/{id}")
    suspend fun getUserDPFromCloud(
        @Path("id") id: Int,
        @Header("x-api-key") token: String,
    ): UserAvtarData
}
