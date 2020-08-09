package com.hoverdroids.noteswitharchitecturecomponents.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hoverdroids.noteswitharchitecturecomponents.database.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 *
 * NOTE: A repository allows the viewModels to get the data they need without caring about whether that data came from the database or an API
 * Base on given criteria, the repository pull from one or the other to abstract the different components so they don't need to rely on any
 * knowledge of the
 */
class NotesRepository(application: Application, private val coroutineScope: CoroutineScope) {

    private val noteDao = NotesDatabase.getDatabase(application, coroutineScope).noteDao()

    fun insert(note: Note) {
        coroutineScope.launch(Dispatchers.IO) { noteDao.insert(note) }
    }

    fun update(note: Note) {
        coroutineScope.launch(Dispatchers.IO) { noteDao.update(note) }
    }

    fun delete(note: Note) {
        coroutineScope.launch(Dispatchers.IO) { noteDao.delete(note) }
    }

    fun deleteAll() {
        coroutineScope.launch(Dispatchers.IO) { noteDao.deleteAllNotes() }
    }

    fun getNote(id: Int): LiveData<Note> {
        return noteDao.getNote(id)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return noteDao.getAllNotes()
    }
}