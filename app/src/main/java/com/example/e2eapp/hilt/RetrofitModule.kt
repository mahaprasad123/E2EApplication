package com.example.e2eapp.hilt

import com.example.e2eapp.data.network.IDashboardService
import com.example.e2eapp.data.network.IEmailDetailsService
import com.example.e2eapp.data.repository.DashboardRepoImpl
import com.example.e2eapp.data.repository.EmailDetailsRepoImpl
import com.example.e2eapp.domain.repo.DashboardRepo
import com.example.e2eapp.domain.repo.EmailDetailsRepo
import com.example.e2eapp.provideAPIKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DashboardRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DetailsRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DetailsDpRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DetailsService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserDPService

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {
    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        const val BASE_URL_DASHBOARD = "https://66e4784bd2405277ed14692e.mockapi.io/api/v1/"
        const val BASE_URL_DETAILS = "https://dummy-json.mock.beeceptor.com/"
        const val BASE_URL_DETAILS_DP = "https://reqres.in/api/"
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    @DashboardRetrofit
    fun provideDashboardRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL_DASHBOARD)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    @DetailsRetrofit
    fun provideDetailsRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL_DETAILS)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    @DetailsDpRetrofit
    fun provideDetailsDpRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL_DETAILS_DP)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideIDashboardService(
        @DashboardRetrofit retrofit: Retrofit,
    ): IDashboardService = retrofit.create(IDashboardService::class.java)

    @Provides
    @Singleton
    @DetailsService
    fun provideIEmailDetailsService(
        @DetailsRetrofit detailsRetrofit: Retrofit,
    ): IEmailDetailsService = detailsRetrofit.create(IEmailDetailsService::class.java)

    @Provides
    @Singleton
    @UserDPService
    fun provideUserDPService(
        @DetailsDpRetrofit detailsDpRetrofit: Retrofit,
    ): IEmailDetailsService = detailsDpRetrofit.create(IEmailDetailsService::class.java)

    @Provides
    @Singleton
    fun provideDashboardRepo(iDashboardService: IDashboardService): DashboardRepo = DashboardRepoImpl(iDashboardService)

    @Provides
    @Singleton
    fun provideEmailDetailsRepo(
        @DetailsService detailsService: IEmailDetailsService,
        @UserDPService userDPService: IEmailDetailsService,
    ): EmailDetailsRepo = EmailDetailsRepoImpl(detailsService, userDPService, 0, provideAPIKey())
}
