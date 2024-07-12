package features.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.SpaceMonoFamily

@Composable
fun CustomButtonComponent(
    backgroundColor: Color = Color.Blue,
    textColor: Color = Color.White,
    rounded: Int = 5,
    text: String,
    onclick: () -> Unit = {}
) {
    Box (
        modifier = Modifier
            .graphicsLayer {
                this.shape = RoundedCornerShape((rounded).dp)
                this.clip = true
            }
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                onclick()
            }
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontFamily = SpaceMonoFamily()
        )
    }
}