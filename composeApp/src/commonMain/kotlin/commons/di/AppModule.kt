package commons.di

import androidx.navigation.NavHostController
import database.RoomDBResources
import database.getRoomDatabase
import features.notes.data.datasources.NoteLocalDataSource
import features.notes.data.datasources.NoteLocalDataSourceImpl
import features.notes.data.repositories.NoteRepositoryImpl
import features.notes.domain.repositories.NoteRepository
import features.notes.domain.usecases.*
import features.notes.ui.controllers.NoteViewModel
import org.kodein.di.*

fun appModule(
    roomDBResources: RoomDBResources,
    navController: NavHostController
) : DI.Module = DI.Module("AppBinding") {
    
    bindSingleton<NavHostController>() { navController }
    
    bindSingleton<RoomDBResources>() { roomDBResources }
    
    bind<NoteLocalDataSource>() with singleton { 
        //val roomDBResources: RoomDBResources = instance()
        getRoomDatabase(builder = roomDBResources.builder()).noteDao() 
    }
    
    bind<NoteLocalDataSourceImpl>() with singleton { 
        NoteLocalDataSourceImpl(noteDao =  instance()) 
    }
    
    bind<NoteRepository>() with singleton { 
        NoteRepositoryImpl(noteLocalDataSource = instance()) 
    }
    
    bind<InsertNoteUsecase>() with singleton { 
        InsertNoteUsecase(noteRepository = instance()) 
    }
    
    bind<UpdateNoteUsecase>() with singleton { 
        UpdateNoteUsecase(noteRepository = instance()) 
    }
    
    bind<GetNoteByIdUsecase>() with singleton { 
        GetNoteByIdUsecase(noteRepository = instance()) 
    }
    
    bind<GetAllNoteUsecase>() with singleton { 
        GetAllNoteUsecase(noteRepository = instance()) 
    }
    
    bind<GetNNoteUsecase>() with singleton { 
        GetNNoteUsecase(noteRepository = instance()) 
    }
    
    bind<DeleteNoteByIdUsecase>() with singleton { 
        DeleteNoteByIdUsecase(noteRepository = instance()) 
    }
    
    bindSingleton <NoteViewModel>() { 
        NoteViewModel(
            localDataSource = instance(),
            insertNoteUsecase = instance(),
            updateNoteUsecase = instance(),
            getAllNoteUsecase = instance(),
            getNoteByIdUsecase = instance(),
            getNNoteUsecase = instance(),
            deleteNoteByIdUsecase = instance(),
        ) 
    }
}