package com.example.e2eapp

import com.example.e2eapp.domain.dto.DashboardEmailData
import com.example.e2eapp.domain.repo.DashboardRepo
import com.example.e2eapp.domain.usecase.DashboardUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UseCaseTest {
    val repo: DashboardRepo = mockk<DashboardRepo>()
    val useCase = DashboardUseCase(repo)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUseCase() {
        runTest {
            coEvery { repo.getDashboardDetails() } returns
                flow {
                    emit(
                        listOf(
                            DashboardEmailData(
                                "1",
                                "",
                                "testSubject",
                                "",
                                "",
                                "",
                                true,
                            ),
                        ),
                    )
                }
            // use below if you have defined  val useCase = mockk<DashboardUseCase>() instead of
            // real object as if we create a mockk object we should explicitly define what to do with the object
            // but in this case real class is not tested, you are only testing mock class so coverage wont be counted
            // Always use real class object and mock all the dependencies
            // coEvery { useCase.getDashboardUseCase() } returns repo.getDashboardDetails()

            val result = useCase.getDashboardUseCase().first()
            advanceUntilIdle()

            coVerify { repo.getDashboardDetails() }
            assert(result[0].subject == "testSubject")
        }
    }
}
