package com.example.myeventsmanagmentapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myeventsmanagmentapp.data.entity.Tags
import com.example.myeventsmanagmentapp.ui.theme.PrimaryColor

@Composable
fun AddTagsListView(list: List<Tags>, onTagClick: (Tags) -> Unit) {
    Column(modifier = Modifier.wrapContentSize()) {
        Text(
            text = "Tags",
            modifier = Modifier
                .padding(5.dp)
                .clickable { },
            color = Color.White
        )
        LazyRow(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(list) {
                Box(
                    Modifier
                        .background(
                            Color(it.color.toInt()).copy(0.2F),
                            shape = RoundedCornerShape(40)
                        )
                        .border(1.dp, Color(it.color.toInt()), shape = RoundedCornerShape(40))
                        .padding(vertical = 2.dp, horizontal = 8.dp)
                        .weight(1f)
                        .clickable {
                            onTagClick.invoke(it)
                        }
                ) {
                    Text(
                        text = it.name,
                        modifier = Modifier.padding(8.dp),
                        color = Color(it.color.toInt())
                    )
                }
            }

        }

        Text(
            text = "+ Add new tag",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)

                .clickable { },
            color = PrimaryColor,
            textAlign = TextAlign.Center
        )

    }
}