package features.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.SpaceMonoFamily
import kotlinx.coroutines.launch


@Composable
fun CustomTextField(
    initialValue: String,
    placeholder: String,
    backgroundColor: Color = Color.Transparent,
    onValueChange: (String) -> Unit,
    fontSize: Int? = null,
    fillSize: Boolean = false,
    maxLength: Int = 100,
) {
    val scope = rememberCoroutineScope() // Create a coroutine scope
    
    var text by remember { mutableStateOf(initialValue) }
    
    val modifier = if(fillSize) Modifier.fillMaxSize()
        else Modifier.fillMaxWidth()
    
    var currentProgress by remember { mutableStateOf(0f) }
        
    Box (
        modifier = modifier
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 10.dp),
    ) {
        if(text.isEmpty()) {
            Text(
                text = placeholder,
                color = Color.White.copy(alpha = 0.2f),
                fontSize = 30.sp,
                fontFamily = SpaceMonoFamily(),
                modifier = modifier
                    .background(Color.Transparent)
                    .align(Alignment.Center)
            )
        }
        
        Column(
            modifier = Modifier.fillMaxSize()
            .padding(top = 30.dp)
        ) {
            BasicTextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                    onValueChange(newText)
                    scope.launch {
                        val progression = (newText.length * 100) / maxLength
                        currentProgress = progression.toFloat() * 0.01f
                    }
                },
                modifier = modifier
                    .background(Color.Transparent),
                singleLine = false,
                cursorBrush = SolidColor(Color.White),
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions(),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = (if(fontSize != null) (fontSize).sp else TextUnit.Unspecified),
                    fontFamily = SpaceMonoFamily(),
                )
            )
            Spacer(Modifier.padding(vertical = 10.dp))
            LinearProgressIndicator(
                progress = { currentProgress },
                trackColor = Color.White.copy(alpha = 0.2f),
                color = Color.Blue,
                modifier = Modifier.fillMaxWidth()
                    .graphicsLayer {
                        this.clip = true
                        this.shape = RoundedCornerShape(10.dp)
                    },
            )
            
            Text(
                text = "${text.length}/$maxLength",
                color = Color.White.copy(alpha = 0.2f),
                fontFamily = SpaceMonoFamily(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right,
                modifier = modifier
                    .background(Color.Transparent)
                    .align(Alignment.End)
            )
        }
        
        
    }
}