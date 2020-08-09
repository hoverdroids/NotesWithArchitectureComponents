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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//NOTE: deviating from example by Coding in Flow; using Google's example here
@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        private const val clearDatabaseEachTime = false

        fun getDatabase(context: Context, coroutineScope: CoroutineScope): NotesDatabase {
            //If the INSTANCE is not null then return it. Otherwise, create the database.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "note-database")
                    //.fallbackToDestructiveMigration()
                    .addCallback(NoteDatabaseCallback(coroutineScope, context.resources))
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

    //This gets called when the database is being created or opened and allows us to add default notes
    private class NoteDatabaseCallback(val scope: CoroutineScope, val resources: Resources): RoomDatabase.Callback() {

        /**
         * Override the onOpen method to populate the database.
         * For this sample, we clear the database every time it is created or opened.
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let{ database ->
                scope.launch(Dispatchers.IO){

                    // Start the app with a clean database every time.
                    // Not needed if you only populate on creation.
                    if(clearDatabaseEachTime) database.noteDao().deleteAllNotes()

                    populateDatabaseFromJson(database.noteDao(), R.raw.notes)
                    populateDatabaseFromCode(database.noteDao(), Note(12343, "Title11", "sdseeeedd", 1))
                }
            }
        }

        private suspend fun populateDatabaseFromJson(noteDao: NoteDao, jsonResId: Int) {
            val json = resources.openRawResource(jsonResId).bufferedReader().use {
                it.readText()
            }
            val typeToken = object : TypeToken<List<Note>>() {}.type
            val notes = Gson().fromJson<List<Note>>(json, typeToken)
            noteDao.insertAllNotes(notes)
        }

        private suspend fun populateDatabaseFromCode(noteDao: NoteDao, note: Note) {
            noteDao.insert(note)
        }
    }
}