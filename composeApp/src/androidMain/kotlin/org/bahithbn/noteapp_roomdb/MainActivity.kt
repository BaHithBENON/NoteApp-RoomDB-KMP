package org.bahithbn.noteapp_roomdb

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import database.RoomDBResources

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        
        enableEdgeToEdge()
        
        super.onCreate(savedInstanceState)
        
        val roomDBResources = RoomDBResources(this);
        
        setContent {
            App(roomDBResources = roomDBResources)
        }
    }
}