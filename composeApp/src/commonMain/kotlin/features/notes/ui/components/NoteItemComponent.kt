package features.notes.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import commons.navigation.AppRoutesValues
import core.SpaceMonoFamily
import features.notes.data.models.ModelNote
import features.notes.ui.NoteDetailsUI
import features.notes.ui.controllers.NoteEvents
import features.notes.ui.controllers.NoteStates
import features.notes.ui.controllers.NoteViewModel
import kotlinx.coroutines.delay
import org.kodein.di.compose.rememberInstance
import kotlin.math.abs
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun noteItemComponent(
    note: ModelNote,
    colorShift: Float,
    rotationAngle: Float,
    yOffset: Int,
    interpolatedColor: Color,
    onDetails: (Int) -> Unit = {}
) {
    
    val viewModel: NoteViewModel by rememberInstance()
    val state: NoteStates by viewModel.uiState.collectAsState()
    
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                println("Scroll : $colorShift" )
                val factor = .8f
                val decayFactor = exp(-colorShift * factor * .5f) // Décroissance exponentielle
                this.rotationZ = abs(yOffset) * factor * (decayFactor)
                shape = RoundedCornerShape(10.dp) // CircleShape
                clip = true
            }
            .clickable {
                if(note.id != null) onDetails(note.id)
            },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = interpolatedColor.copy(alpha = .9f)
                ),
        ) {
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = note.title,
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = SpaceMonoFamily()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = note.content,
                        maxLines = 3,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = SpaceMonoFamily()
                    )
                }
                
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
                            viewModel.onUiEvent(NoteEvents.DeleteNote(note))
                        },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteBackground(
    swipeToDismissBoxState: DismissState
) {
    val color = if(swipeToDismissBoxState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else Color.Transparent
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuratiion: Int = 500,
    content: @Composable (T) -> Unit,
    
    colorShift: Float,
    rotationAngle: Float,
    yOffset: Int
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    
    val state = rememberDismissState(
        confirmStateChange = { value ->
            if(value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )
    
    LaunchedEffect(
        key1 = isRemoved
    ) {
        if(isRemoved) {
            delay(animationDuratiion.toLong())
            onDelete(item)
        }
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                //this.translationY = abs(yOffset) * 0.5f // Ajustez cette valeur selon vos besoins
                // this.translationY = rotationAngle * 0.5f // Ajustez cette valeur selon vos besoins
                // this.rotationZ = rotationAngle
                this.rotationZ = abs(yOffset) * 0.50f 
            }
            .background(Color.Blue)
            .clip(RoundedCornerShape(8.dp)),
            // .offset(x = yOffset.dp)
        shape = RoundedCornerShape(5.dp),
        //color = Color.Blue,
    ) {
        AnimatedVisibility(
            visible = !isRemoved,
            exit = shrinkVertically (
                animationSpec = tween(durationMillis = animationDuratiion),
                shrinkTowards = Alignment.Top
            ) + fadeOut(),
            modifier = Modifier.clip(RoundedCornerShape(5.dp))
        ) {
            SwipeToDismiss(
                state = state,
                background = {
                    DeleteBackground(swipeToDismissBoxState = state)
                },
                dismissContent = { content(item) },
                directions = setOf(DismissDirection.EndToStart)
            )
        }
    }
}

fun goToUpdateNote(navigator: Navigator, note: ModelNote) {
    navigator.push(AppRoutesValues.UpdateNoteUi)
}