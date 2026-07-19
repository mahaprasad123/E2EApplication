package com.example.e2eapp.data.repository

import android.util.Log
import com.example.e2eapp.data.network.IEmailDetailsService
import com.example.e2eapp.domain.EmailDetailsResult
import com.example.e2eapp.domain.dto.EmailDetailsData
import com.example.e2eapp.domain.repo.EmailDetailsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion

class EmailDetailsRepoImpl(
    private val detailsService: IEmailDetailsService,
    private val userDPService: IEmailDetailsService,
    var id: Int,
    var token: String,
) : EmailDetailsRepo {
    override suspend fun fetchSenderDetails(): Flow<EmailDetailsResult> =
        flow<EmailDetailsResult> {
            val userDetails = detailsService.getUserDetailsFromCloud()
            userDetails.forEach {
                val data =
                    EmailDetailsData(
                        name = "dummy",
                        continentName = it.name ?: "",
                        developedCountries = it.developedCountries,
                        message = "dummyMessage",
                    )
                delay(1000)
                emit(EmailDetailsResult.UserDetails(data))
            }
        }.catch { e ->
            emit(EmailDetailsResult.UserDetailsError(e.toString()))
        }.onCompletion {
            Log.d("MPC", "user details Completed")
        }.flowOn(Dispatchers.IO)

    override suspend fun fetchSenderDP(
        id: Int,
        token: String,
    ): Flow<String> =
        flow {
            Log.d("data2", "id = $id token = $token")
            val dp = userDPService.getUserDPFromCloud(id, token)
            Log.d("data22", "dp = $dp")
            emit(dp.data.avatar)
        }.flowOn(Dispatchers.IO)
            .catch {
                emit("error ${it.message}")
            }
}
