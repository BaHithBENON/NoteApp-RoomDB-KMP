package features.notes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import core.SpaceMonoFamily
import core.theme.scaffoldColor
import features.notes.ui.components.CustomButtonComponent
import features.notes.ui.components.noteItemComponent
import features.notes.ui.controllers.NoteEvents
import features.notes.ui.controllers.NoteStates
import features.notes.ui.controllers.NoteViewModel
import org.kodein.di.compose.rememberInstance
import kotlin.math.min

object NotesOverviewUI : Screen {
    @Composable
    override fun Content() {
        NotesOverviewUI()
    }
}

@Composable
fun NotesOverviewUI(
    onDetails: (Int) -> Unit = {},
    onAddNote: () -> Unit = {}
) {
    
    val viewModel: NoteViewModel by rememberInstance()
    val state: NoteStates by viewModel.uiState.collectAsState()
    
    val lazyListState = rememberLazyListState()
    val scrollProgress = min(lazyListState.firstVisibleItemIndex.toFloat(), lazyListState.layoutInfo.visibleItemsInfo.size.toFloat())
    val scrollOffset = lazyListState.firstVisibleItemIndex.toFloat()
    
    val maxScroll = 1000f // Maximum scroll value for full interpolation

    // Observe scroll position
    val scrollOffset2 by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemScrollOffset + lazyListState.firstVisibleItemIndex * 100f
        }
    }
    
    // Interpolate between Blue and Red based on scrollOffset
    val interpolatedColor = interpolateColor(Color.Blue, Color.Red, min(scrollOffset2 / maxScroll, 1f))
    
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .background(scaffoldColor),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNote,
                backgroundColor = Color.Blue,
                content = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add new note",
                        tint = Color.White
                    )
                }
            ) 
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(scaffoldColor)
        ) {
            if(state.notes.isNotEmpty()) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .background(Color.Transparent),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    itemsIndexed(state.notes) { index, note ->
                        val yOffset = index * 5
                        val rotationAngle = scrollOffset * 50f
                        val colorShift = scrollProgress * 10f
                        noteItemComponent(note = note, rotationAngle = rotationAngle, colorShift = colorShift,
                            yOffset = yOffset, interpolatedColor = interpolatedColor, onDetails = onDetails
                        )
                        
                        /*
                        SwipeToDeleteContainer(
                            item = note,
                            onDelete = { n ->
                                viewModel.onUiEvent(NoteEvents.DeleteNote(n))
                            },
                            content = {
                                noteItemComponent(note = note, rotationAngle = rotationAngle, colorShift = colorShift,
                                            yOffset = yOffset, interpolatedColor = interpolatedColor)
                            },
                            colorShift = colorShift,
                            yOffset = yOffset,
                            rotationAngle = rotationAngle
                        )
                        */
                    }
                }
            } else {
                Box (
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Nothing for the moment ...",
                            fontSize = 30.sp,
                            fontFamily = SpaceMonoFamily(),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                        
                        CustomButtonComponent(
                            text = "Reload",
                            onclick = {
                                viewModel.onUiEvent(NoteEvents.ListNotes)
                            }
                        )
                    }
                }
            }
        }
    }
}

fun interpolateColor(startColor: Color, endColor: Color, fraction: Float): Color {
    val red = (startColor.red + (endColor.red - startColor.red) * fraction)
    val green = (startColor.green + (endColor.green - startColor.green) * fraction)
    val blue = (startColor.blue + (endColor.blue - startColor.blue) * fraction)
    val alpha = (startColor.alpha + (endColor.alpha - startColor.alpha) * fraction)
    return Color(red, green, blue, alpha)
}