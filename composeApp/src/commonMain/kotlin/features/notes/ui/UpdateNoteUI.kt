package features.notes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cafe.adriel.voyager.core.screen.Screen
import core.theme.scaffoldColor
import features.notes.data.models.ModelNote
import features.notes.ui.components.CustomButtonComponent
import features.notes.ui.components.CustomTextField
import features.notes.ui.controllers.NoteEvents
import features.notes.ui.controllers.NoteViewModel
import features.notes.ui.controllers.UpsertNoteStates
import org.kodein.di.compose.rememberInstance

object UpdateNoteUI : Screen {
    @Composable
    override fun Content() {
        UpsertNoteUI(noteId = 0)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertNoteUI(
    noteId: Int?
) : Unit {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    
    val navController : NavHostController by rememberInstance()
        
    val viewModel: NoteViewModel by rememberInstance()
    val state: UpsertNoteStates by viewModel.upsertUiState.collectAsState()
    
    var title by rememberSaveable { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    
    Scaffold (
        modifier = Modifier.fillMaxSize()
            .background(scaffoldColor)
            .safeDrawingPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        // topBar = { topBarComponent(scrollBehavior = scrollBehavior) }
    ) {
        Column(modifier = Modifier.fillMaxSize()
            .background(scaffoldColor)
            .padding(horizontal = 10.dp)
        ) {
            
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                CustomButtonComponent(
                    text = "Save",
                    onclick = {
                        val newNote = ModelNote(
                            title = title,
                            content = content,
                            description = description,
                            addedAt = 1L,
                            id = null
                        )
                        println("Click = ${newNote.title}")
                        viewModel.onUiEvent(NoteEvents.UpsertNote(note = newNote))
                        navController.popBackStack()
                    }
                )
                
                Spacer(Modifier.padding(horizontal = 0.dp))
                
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .graphicsLayer {
                            this.shape = RoundedCornerShape(5.dp) // CircleShape
                            this.clip = true
                        }
                        .padding(5.dp)
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
            }
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                // Title
                item {
                    CustomTextField(
                        initialValue = title,
                        placeholder = "Title of your note...",
                        backgroundColor = scaffoldColor,
                        fontSize = 25,
                        onValueChange = { newValue ->
                            title = newValue
                        }
                    )
                }
                // Description
                item {
                    // Content TextField
                    CustomTextField(
                        initialValue = description,
                        placeholder = "Description...",
                        fontSize = 20,
                        maxLength = 200,
                        // backgroundColor = Color.LightGray.copy(alpha = .3f),
                        fillSize = true,
                        onValueChange = { newValue ->
                            description = newValue
                        }
                    )
                }
                // Content
                item {
                    // Content TextField
                    CustomTextField(
                        initialValue = content,
                        placeholder = "Content...",
                        fillSize = true,
                        maxLength = 2000,
                        onValueChange = { newValue ->
                            content = newValue
                        }
                    )
                }
            }
        }
    }
}