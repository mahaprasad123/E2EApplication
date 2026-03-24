package com.example.e2eapp

import com.example.e2eapp.domain.dto.DashboardEmailData
import com.example.e2eapp.domain.usecase.DashboardUseCase
import com.example.e2eapp.presentation.viewmodel.DashboardViewmodel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewmodelTest {
    private lateinit var viewmodel: DashboardViewmodel
    private val dashboardUseCase = mockk<DashboardUseCase>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // This is required as ourViewmodelscope runs in Dispatchers.Main,
        // but our JVM based UT runs in Dispatchers.Unconfined
        viewmodel = DashboardViewmodel(dashboardUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun checkDashboardData() =
        runTest(testDispatcher) {
            // Given

            val mockData =
                listOf(
                    DashboardEmailData(
                        "1",
                        "Mahi",
                        "test",
                        "test",
                        "test",
                        "test",
                        false,
                    ),
                )
            coEvery { dashboardUseCase.getDashboardUseCase() } returns flowOf(mockData)

            // When
            viewmodel.getDashboardDataFromCloud()
            advanceUntilIdle()

            // Then
            coVerify { dashboardUseCase.getDashboardUseCase() }
            assert(viewmodel.dashboardData.value.isNotEmpty())
            assert(viewmodel.dashboardData.value[0].from == "Mahi")
        }
}
