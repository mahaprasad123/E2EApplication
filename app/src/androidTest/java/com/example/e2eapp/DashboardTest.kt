package com.example.e2eapp

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.e2eapp.domain.dto.DashboardEmailData
import com.example.e2eapp.domain.repo.DashboardRepo
import com.example.e2eapp.domain.usecase.DashboardUseCase
import com.example.e2eapp.navigation.LocalNavController
import com.example.e2eapp.presentation.screens.Dashboard
import com.example.e2eapp.presentation.viewmodel.DashboardViewmodel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DashboardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var fakeRepo: DashboardRepo


    @Before
    fun before() {
        // 1. Create mock data
        val mockData = listOf(
            DashboardEmailData(
                id = "1",
                from = "John Doe",
                subject = "Hello",
                body = "This is a test snippet",
                profileImage = "",
                date = "2023-10-27",
                hasAttachment = false
            )
        )

        // 2. Create a fake repository
        fakeRepo = object : DashboardRepo {
            override suspend fun getDashboardDetails(): Flow<List<DashboardEmailData>> {
                return flowOf(mockData)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDashboardScreenDisplaysData() {

        // 3. Create ViewModel with fake dependencies
        val viewModel = DashboardViewmodel(DashboardUseCase(fakeRepo))

        composeTestRule.setContent {
            val navController = rememberNavController()
            CompositionLocalProvider(LocalNavController provides navController) {
                Dashboard(viewmodel = viewModel, modifier = Modifier)
            }
        }
        // 4. Assertions
        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hello").assertIsDisplayed()
    }
}
