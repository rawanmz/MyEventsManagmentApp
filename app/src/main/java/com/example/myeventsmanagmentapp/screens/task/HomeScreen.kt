package com.example.myeventsmanagmentapp.screens.task

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myeventsmanagmentapp.R
import com.example.myeventsmanagmentapp.component.TaskCategoryCard
import com.example.myeventsmanagmentapp.data.entity.TaskType
import com.example.myeventsmanagmentapp.ui.theme.Navy
import com.example.myeventsmanagmentapp.ui.theme.PrimaryColor
import com.google.firebase.auth.FirebaseUser


@Composable
fun HomeScreen(invoke: FirebaseUser?) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)
        .semantics {
            contentDescription = "Home Screen"
        }) {
        item {
            HeaderView(invoke?.displayName.orEmpty())
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                "My Tasks",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Navy
            )
        }
        //task Type View
        item {
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Column(modifier = Modifier.weight(0.4f).padding(vertical =12.dp)) {
                    TaskCategoryCard(
                        TaskType.Completed.type,
                        "15 Task",
                        Color(0xFF7DC8E7),
                        height = 220.dp,
                        onClick = {},
                        image = {
                            Image(
                                painter = painterResource(id = R.drawable.imac),
                                contentDescription = "",
                                modifier = Modifier.size(80.dp)
                            )

                        })
                    TaskCategoryCard(
                        TaskType.Pending.type,
                        "15 Task",
                        Color(0xFF7D88E7),
                        height = 190.dp,
                        onClick = {},
                        image = {
                            Icon(
                                imageVector = Icons.TwoTone.CheckCircle,
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)

                            )
                        })
                }
                /////
                Column(modifier = Modifier.weight(0.4f).padding(vertical = 12.dp)) {
                    TaskCategoryCard(
                        TaskType.Cancelled.type,
                        "15 Task",
                        Color(0xFFE77D7D),
                        height = 190.dp,
                        onClick = {},
                        image = {
                            Icon(
                                imageVector = Icons.TwoTone.CheckCircle,
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)

                            )
                        })
                    TaskCategoryCard(
                        TaskType.OnGoing.type,
                        "15 Task",
                        Color(0xFF81E89E),
                        height = 220.dp,
                        onClick = {},
                        image = {
                            Image(
                                painter = painterResource(id = R.drawable.imac),
                                contentDescription = "",
                                modifier = Modifier.size(90.dp)
                            )
                        })
                }

            }
        }
        //today task view
        item{
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween

            ) {

                Text(
                    "Today Tasks",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Navy
                )
                Text(
                    "View all",
                    modifier = Modifier
                        .padding(top = 8.dp),
                    fontSize = 12.sp,
                    color = PrimaryColor
                )
            }
        }
    }
}


@Composable
fun HeaderView(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween

    ) {

        Column {
            Text(
                "Hi, $userName",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Navy
            )
            Text(
                "Let's make this day productive",
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }


        Card(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(50),//use 20 if you want to round corners like the one in the design
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),

            ) {
            //todo use coil
            Image(
                painter = painterResource(id = R.drawable.user_avatar_male),
                contentDescription = "profile picture",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop,


                )
        }
    }
}

