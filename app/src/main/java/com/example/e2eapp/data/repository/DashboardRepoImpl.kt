package com.example.e2eapp.data.repository

import android.util.Log
import com.example.e2eapp.data.dto.DashboardMasterData
import com.example.e2eapp.data.network.IDashboardNetwork
import com.example.e2eapp.domain.dto.DashboardEmailData
import com.example.e2eapp.domain.repo.DashboardRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import okhttp3.Dispatcher
import javax.inject.Inject

class DashboardRepoImpl @Inject constructor(val iDashboardNetwork: IDashboardNetwork) :
    DashboardRepo {

    override suspend fun getDashboardDetails(): Flow<List<DashboardEmailData>> =
        flowOf(
            iDashboardNetwork.getDashboardDataFromCloud()
                .map {
                    DashboardEmailData(
                        it.id,
                        it.payload.from,
                        it.payload.subject,
                        it.snippet,
                        it.payload.profileImage,
                        it.payload.date,
                        it.hasAttachments
                    )
                }).flowOn(Dispatchers.IO).onStart {
            Log.d("MPC-DashboardRepoImpl", "getDashboardDetails -  START")
        }.onEach {
            Log.d("MPC-DashboardRepoImpl", "getDashboardDetails - data=$it")
        }.catch {
            Log.e("MPC-DashboardRepoImpl", "Error -  $it")
        }
}