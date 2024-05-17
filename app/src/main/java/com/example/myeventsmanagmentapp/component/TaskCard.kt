package com.example.myeventsmanagmentapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myeventsmanagmentapp.data.entity.Tags
import com.example.myeventsmanagmentapp.ui.theme.Navy
import com.example.myeventsmanagmentapp.ui.theme.PrimaryColor

@Composable
fun TaskCard(taskTitle: String, timeFrom: String?, timeTo: String?, tag: Tags?) {
    val dividerHeight = remember {
        mutableStateOf(50.dp)
    }
    Color.White.toArgb().toShort()
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(
                tag?.color?.toIntOrNull() ?: PrimaryColor.toArgb()
            ).copy(0.1f)
        )

    ) {
        Column() {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp), Arrangement.SpaceBetween
            ) {

                Row {
                    //divider
                    Box(
                        modifier = Modifier
                            .height(dividerHeight.value)
                            .width(3.dp)
                            .background(
                                Color(tag?.color?.toIntOrNull() ?: PrimaryColor.toArgb()),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(0.dp, 40.dp)
                    )
                    Column(modifier = Modifier
                        .padding(4.dp)
//                        .drawBehind {
//                            drawLine(
//                                Color(tag?.color?.toIntOrNull() ?: PrimaryColor.toArgb()),
//                                Offset(0f, 0F),
//                                Offset(0F, size.height),
//                                2.dp.toPx(),)
//                        }
                        .onGloballyPositioned {
                            dividerHeight.value = it.size.height.dp / 2
                        }) {
                        Text(
                            text = taskTitle,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Navy
                        )
                        Text(text = "$timeFrom - $timeTo", fontSize = 15.sp, color = Color.Gray)
                    }
                }
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )

            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(25.dp, 10.dp), Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    Modifier
                        .background(
                            Color(tag?.color?.toIntOrNull() ?: PrimaryColor.toArgb()).copy(0.4f),
                            RoundedCornerShape(16.dp)
                        )
//                        .border(
//                            1.dp,
//                            Color(tag?.color?.toIntOrNull() ?: PrimaryColor.toArgb()),
//                            RoundedCornerShape(16.dp)
//                        )
                ) {
                    Text(
                        text = tag?.name.orEmpty(),
                        modifier = Modifier.padding(5.dp),
                        color = Color.White
                    )
                }
            }
        }

    }
//to convert color to String and vice versa
//    val color=Color.Gray.toArgb().toString()
//    Color(color.toIntOrNull()?: PrimaryColor.toArgb())

}