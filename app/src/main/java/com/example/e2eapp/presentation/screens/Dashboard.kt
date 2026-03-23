package com.example.e2eapp.presentation.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.e2eapp.R
import com.example.e2eapp.navigation.LocalNavController
import com.example.e2eapp.navigation.Screen
import com.example.e2eapp.presentation.viewmodel.DashboardViewmodel
import com.example.e2eapp.saveValueToStore
import kotlinx.coroutines.launch

@Composable
fun Dashboard(viewmodel: DashboardViewmodel, modifier: Modifier) {
    LaunchedEffect(Unit) {
        viewmodel.getDashboardDataFromCloud()
    }
    val list by viewmodel.dashboardData.collectAsState()

    ////////////testing codes//////////////
    val listId = mutableListOf<String>()
    val listEmail = mutableListOf<String>()
    val listFinal = mutableListOf<TestFlatmap>()
    list.forEach { data ->
        listId.add(data.id)
        listEmail.add(data.from)
        listFinal.add(TestFlatmap(listId, listEmail))
    }
    Log.d("MPC-Greeting", "updatedList=$listFinal")

    val emails = listFinal.flatMap {
        it.email
    }
    Log.d("MPC-Greeting", "emails=$emails")
    //////////end of test/////////////////

    if (list.isEmpty())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x4D000000)), Alignment.Center
        ) {
            Text("Loading...Please wait")
        }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(list) { index, item ->
            val controller = LocalNavController.current
            /*
            *****
            *****UI Starts from here
            *****
             */
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(10.dp, 10.dp)
            ) {

                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                        .clickable {
                            scope.launch {
                                saveValueToStore(context, item.from)
                                controller.navigate(Screen.Details(item.id))
                            }
                        },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = item.profileImage,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(CircleShape)
                            .weight(.15f)
                            .background(Color.Cyan),
                        contentScale = ContentScale.Fit


                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(15.dp)
                    )
                    Column(modifier = Modifier.weight(if (item.hasAttachment) .75f else .85f)) {
                        Text(
                            text = item.from,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = item.subject,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                    if (item.hasAttachment)
                        Image(
                            painter = painterResource(R.drawable.attachment),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(0.1f)
                                .padding(10.dp)
                        )
                }

            }
        }
    }
}

data class TestFlatmap(val rIdList: List<String>, val email: List<String>)