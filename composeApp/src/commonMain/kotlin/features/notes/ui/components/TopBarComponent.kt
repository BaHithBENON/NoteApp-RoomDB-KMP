package features.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import core.theme.scaffoldColor
import org.kodein.di.compose.rememberInstance

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarComponent(
    scrollBehavior: TopAppBarScrollBehavior
) {
    
    val navController: NavHostController by rememberInstance()
    
    TopAppBar (
        title = {},
        actions = {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .graphicsLayer {
                        this.shape = RoundedCornerShape(10.dp) // CircleShape
                        this.clip = true
                    }
                    .padding(10.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = Modifier
            .background(scaffoldColor)
            .fillMaxWidth()
    )
}