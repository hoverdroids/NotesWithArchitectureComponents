package com.hoverdroids.noteswitharchitecturecomponents.database

import android.content.Context
import android.content.res.Resources
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hoverdroids.noteswitharchitecturecomponents.R
import com.hoverdroids.noteswitharchitecturecomponents.database.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//NOTE: deviating from example by Coding in Flow; using Google's example here
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    //This gets called when the database is being created or opened and allows us to add default notes
    private class NoteDatabaseCallback(val scope: CoroutineScope, val resources: Resources): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let{ database ->
                scope.launch{
                    val noteDao = database.noteDao()
                    prePopulateDatabase(noteDao)
                }
            }
        }

        private suspend fun prePopulateDatabase(noteDao: NoteDao) {
            val json = resources.openRawResource(R.raw.notes).bufferedReader().use {
                it.readText()
            }
            val typeToken = object : TypeToken<List<Note>>() {}.type
            val notes = Gson().fromJson<List<Note>>(json, typeToken)
            noteDao.insertAllNotes(notes)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope): NoteDatabase {
            INSTANCE?.let { return it }

            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note-database")
                    //.addCallback(NoteDatabaseCallback(coroutineScope, context.resources))
                    .build()
                return INSTANCE!!
            }
        }
    }
}