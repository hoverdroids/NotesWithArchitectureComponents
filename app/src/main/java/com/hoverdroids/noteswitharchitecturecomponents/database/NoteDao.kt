package com.hoverdroids.noteswitharchitecturecomponents.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hoverdroids.noteswitharchitecturecomponents.database.entities.Note

//NOTE: It's typically good practice to make one DAO per entity. Depends on the case though.
//Room uses this to auto-generates the code files.
@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllNotes(notes: List<Note>)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Delete FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table WHERE id = :id")
    fun getNote(id: Int): LiveData<Note>

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>
}