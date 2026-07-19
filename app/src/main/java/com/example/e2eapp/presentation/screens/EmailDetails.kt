package com.example.e2eapp.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.e2eapp.SENDER_KEY
import com.example.e2eapp.domain.dto.EmailDetailsData
import com.example.e2eapp.getValueFromStore
import com.example.e2eapp.navigation.LocalNavController
import com.example.e2eapp.presentation.viewmodel.EmailDetailsViewmodel

@Composable
fun EmailDetails(
    id: Int?,
    message: String,
    modifier: Modifier,
) {
    val controller = LocalNavController.current
    val context = LocalContext.current
    val name by getValueFromStore(context, SENDER_KEY).collectAsState("name")
    val viewmodel = hiltViewModel<EmailDetailsViewmodel>()

    val dp by viewmodel.profilePhoto.collectAsState()
    val details by viewmodel.senderDetails.collectAsState()

    LaunchedEffect(Unit) {
        viewmodel.fetchSenderDP(id ?: 0)
        viewmodel.fetchSenderDetails()
    }

    Card(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(20.dp, 50.dp),
        elevation = CardDefaults.cardElevation(3.dp, pressedElevation = 7.dp),
        shape = RoundedCornerShape(5.dp),
    ) {
        // Show loading or the content based on whether details are available
        if (details.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Loading details...")
            }
        } else {
            var positionValue = EmailDetailsData("", "*****", listOf("*****"), "")
            // Safely find the item by ID or fallback to the first item
            if (id != null) {
                positionValue = if (id >= 0 && id < details.size) details[id] else positionValue
            }

            Box(contentAlignment = Alignment.CenterStart) {
                Column(modifier = Modifier.padding(20.dp)) {
                    AsyncImage(
                        model = dp,
                        contentDescription = "Profile Photo",
                        modifier =
                            Modifier
                                .size(150.dp)
                                .background(Color.Cyan),
                    )
                    Spacer(Modifier.height(70.dp))
                    Text("Name: $name")
                    HorizontalDivider(Modifier.padding(vertical = 8.dp))
                    Text("Message: $message")
                    HorizontalDivider(Modifier.padding(vertical = 8.dp))
                    Text("Continent: ${positionValue.continentName ?: "*****"}")
                    HorizontalDivider(Modifier.padding(vertical = 8.dp))
                    Text("Developed countries: ${positionValue.developedCountries.joinToString(", ") ?: "*****"}")
                    HorizontalDivider(Modifier.padding(vertical = 8.dp))
                    Text("Id: $id")
                }
            }
        }
    }

    BackHandler {
        controller.popBackStack()
    }
}
