package com.hoverdroids.noteswitharchitecturecomponents.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hoverdroids.noteswitharchitecturecomponents.database.NotesRepository
import com.hoverdroids.noteswitharchitecturecomponents.database.entities.Note

//DO NOT HOLD references to views or contexts! ViewModels are retained throughout lifecycle configuration changes
class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val repository: NotesRepository = NotesRepository(application, viewModelScope)
    var allNotes: LiveData<List<Note>> = repository.getAllNotes()

    fun insert(note: Note) = repository.insert(note)
    fun update(note: Note) = repository.update(note)
    fun delete(note: Note) = repository.delete(note)
    fun deleteAll() = repository.deleteAll()
}