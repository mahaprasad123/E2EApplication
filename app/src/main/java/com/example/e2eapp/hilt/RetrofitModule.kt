package com.example.e2eapp.hilt

import com.example.e2eapp.data.network.IDashboardNetwork
import com.example.e2eapp.data.repository.DashboardRepoImpl
import com.example.e2eapp.domain.repo.DashboardRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {
    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        const val BASE_URL = "https://66e4784bd2405277ed14692e.mockapi.io/api/v1/"
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideIDashboardNetwork(retrofit: Retrofit): IDashboardNetwork = retrofit.create(IDashboardNetwork::class.java)

    @Provides
    @Singleton
    fun provideDashboardRepo(iDashboardNetwork: IDashboardNetwork): DashboardRepo = DashboardRepoImpl(iDashboardNetwork)
}
