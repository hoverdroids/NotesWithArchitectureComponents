package com.hoverdroids.noteswitharchitecturecomponents.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hoverdroids.noteswitharchitecturecomponents.database.NoteDao
import com.hoverdroids.noteswitharchitecturecomponents.database.NotesDatabase
import com.hoverdroids.noteswitharchitecturecomponents.database.NotesRepository
import com.hoverdroids.noteswitharchitecturecomponents.database.entities.Note

//DO NOT HOLD references to views or contexts! ViewModels are retained throughout lifecycle configuration changes
class NoteViewModel(application: Application): AndroidViewModel(application) {

    //Get a repository the gets/puts data in a given coroutineScope
    private val repository: NotesRepository
    var allNotes: LiveData<List<Note>>

    init {

        val noteDao = NotesDatabase.getDatabase(application, viewModelScope).noteDao()
        repository = NotesRepository(noteDao)
        allNotes = repository.getAllNotes()
    }

    /*fun getAllNotes(): LiveData<List<Note>> {
        return repository.getAllNotes()
    }*/
}