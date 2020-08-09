package com.hoverdroids.noteswitharchitecturecomponents.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hoverdroids.noteswitharchitecturecomponents.database.NotesRepository
import com.hoverdroids.noteswitharchitecturecomponents.database.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//DO NOT HOLD references to views or contexts! ViewModels are retained throughout lifecycle configuration changes
class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val repository: NotesRepository = NotesRepository(application, viewModelScope)
    var allNotes: LiveData<List<Note>> = repository.getAllNotes()

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}