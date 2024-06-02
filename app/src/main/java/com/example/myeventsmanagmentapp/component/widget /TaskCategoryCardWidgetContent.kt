package com.example.myeventsmanagmentapp.component.widget


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.myeventsmanagmentapp.R


@Composable
fun TaskCategoryCardWidgetContent(
    title: String,
    subTitle: String,
    tintColor: Color,
    height: Dp = 180.dp,
    onClick: () -> Unit,
    imageResId: Int
) {

    Column(modifier = GlanceModifier.fillMaxWidth()
        .padding(12.dp)
        .clickable {
            onClick.invoke()
        }
        .background(tintColor)) {
        Box(
            modifier = GlanceModifier.fillMaxSize()
        ) {
            androidx.glance.Image(
                provider = ImageProvider(R.drawable.home_tag_background),
                contentDescription = null,
                modifier = GlanceModifier
                    .size(64.dp),
                colorFilter = androidx.glance.ColorFilter.tint(ColorProvider(tintColor))
            )
            Column(
                modifier = GlanceModifier
                    .padding(16.dp)
            ) {
                Row(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.1f)),
                ) {
                    androidx.glance.Image(
                        provider = ImageProvider(imageResId),
                        contentDescription = "",
                        modifier = GlanceModifier.size(80.dp)
                    )
                    //Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "")
                }
                Text(
                    modifier = GlanceModifier.padding(vertical = 12.dp),
                    text = title
                )
                Text(text = subTitle, style = TextStyle(color = ColorProvider(Color.White)))
            }
        }
    }
}
