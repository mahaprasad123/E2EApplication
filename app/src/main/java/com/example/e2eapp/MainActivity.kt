package com.example.e2eapp

import DiagnosticsImpl
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e2eapp.navigation.InitNavigation
import com.example.e2eapp.presentation.theme.theme.E2EAPPTheme
import com.example.e2eapp.presentation.viewmodel.DashboardViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            E2EAPPTheme {
                val viewmodel: DashboardViewmodel = hiltViewModel()
                var showBottomSheet by remember { mutableStateOf(false) }
                val sheetState = rememberModalBottomSheetState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("E2E App")
                            },
                            actions = {
                                IconButton(onClick = { showBottomSheet = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Settings"
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        InitNavigation(viewmodel)
                    }

                    if (showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showBottomSheet = false },
                            sheetState = sheetState
                        ) {
                            val details = DiagnosticsImpl().getDeviceDetails()
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .padding(bottom = 32.dp)
                            ) {
                                Text(text = "Diagnostics", fontWeight = FontWeight.Bold)
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                                Text(details)
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                                Text("Flavor: ${BuildConfig.FLAVOR}")
                                Text("Build Type: ${BuildConfig.BUILD_TYPE}")
                                Text("Application ID: ${BuildConfig.APPLICATION_ID}")
                            }
                        }
                    }
                }
            }
        }
    }
}
