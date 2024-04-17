package com.stefanoq21.socialcleaningcontrol.presentation.component.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CounterElement(
    imageVector: ImageVector,
    iconColor: Color,
    circleColor: Color,
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = imageVector,
            contentDescription = "avatar",
            tint = iconColor,
            modifier = Modifier
                .size(32.dp)
                //.clip(CircleShape)
                .drawBehind {
                    drawCircle(
                        color = circleColor,
                        radius = 32.dp.toPx()
                    )
                },
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .testTag("CounterElementText"),
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )


    }
}