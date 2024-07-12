package features.notes.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import cafe.adriel.voyager.core.screen.Screen
import core.SpaceMonoFamily
import core.theme.scaffoldColor
import features.notes.ui.controllers.GetNoteStates
import features.notes.ui.controllers.NoteEvents
import features.notes.ui.controllers.NoteViewModel
import kotlinx.coroutines.delay
import org.kodein.di.compose.rememberInstance

object NoteDetailsUI : Screen {
    @Composable
    override fun Content() {
        NoteDetailsUI(noteId = 0)
    }
}

@Composable
fun NoteDetailsUI(
    noteId: Int
) {
    val viewModel: NoteViewModel by rememberInstance()
    
    val state: GetNoteStates by viewModel.detailsNoteUiState.collectAsState()

    LaunchedEffect(noteId) {
        val result = viewModel.getNoteByIdNote(NoteEvents.GetNote(noteId))
    }
    
    val navController : NavHostController by rememberInstance()
    
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(scaffoldColor)
            .safeDrawingPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(scaffoldColor)
        ) { 
            LazyColumn(
                contentPadding = PaddingValues(vertical = 5.dp, horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                // Title
                item {
                    AnimatedContent(
                        delay = 200,
                        content = {
                            Box (
                                modifier = Modifier.fillMaxWidth()
                                    .graphicsLayer {
                                        shape = RoundedCornerShape(10.dp)
                                        clip = true
                                    }
                                    .background(Color.Blue)
                                    .padding(horizontal = 10.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = state.note.title,
                                    fontFamily = SpaceMonoFamily(),
                                    fontSize = 25.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.background(Color.Transparent)
                                )
                                
                                Box(modifier = Modifier
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
                                        .align(Alignment.CenterEnd)
                                        ,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null,
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    )
                }
                // Description
                item {
                    AnimatedContent(
                        delay = 500,
                        content = {
                            Box (
                                modifier = Modifier.fillMaxWidth()
                                    .graphicsLayer {
                                        shape = RoundedCornerShape(10.dp)
                                        clip = true
                                    }
                                    .background(Color.LightGray.copy(alpha = .3f))
                                    .padding(horizontal = 10.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = state.note.description,
                                    fontFamily = SpaceMonoFamily(),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.background(Color.Transparent)
                                )
                            }
                        }
                    )
                }
                // Content
                item {
                    AnimatedContent(
                        delay = 500,
                        content = {
                            Box (
                                modifier = Modifier.fillMaxWidth()
                                    .graphicsLayer {
                                        shape = RoundedCornerShape(10.dp)
                                        clip = true
                                    }
                                    .background(Color.LightGray.copy(alpha = .3f))
                                    .padding(horizontal = 10.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = state.note.content,
                                    fontFamily = SpaceMonoFamily(),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.background(Color.Transparent)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun AnimatedContent(
    content: @Composable () -> Unit,
    delay: Long
) {
    
    var visible by remember { mutableStateOf(false) }
    
    // Use LaunchedEffect to trigger a delay before making the content visible
    LaunchedEffect(Unit) {
        delay(delay) // Delay of 1 second
        visible = true
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(animationSpec = tween(durationMillis = 200 * (.5f).toInt())) { fullWidth ->
            // Offsets the content by 1/3 of its width to the left, and slide towards right
            // Overwrites the default animation with tween for this slide animation.
            -fullWidth / 5
        } + fadeIn(
            // Overwrites the default animation with tween
            animationSpec = tween(durationMillis = 200 * (.5f).toInt())
        ),
        exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
            // Overwrites the ending position of the slide-out to 200 (pixels) to the right
            200
        } + fadeOut()
    ) {
        content()
    }
}