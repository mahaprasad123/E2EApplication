package com.example.e2eapp.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.e2eapp.SENDER_KEY
import com.example.e2eapp.getValueFromStore
import com.example.e2eapp.navigation.LocalNavController

@Composable
fun EmailDetails(id: Int?, modifier: Modifier) {
    val controller = LocalNavController.current
    val context = LocalContext.current
    val name by getValueFromStore(context, SENDER_KEY).collectAsState("name")
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = name ?: "Error", fontWeight = FontWeight.Bold)
    }

    BackHandler {
        controller.popBackStack()
    }
}