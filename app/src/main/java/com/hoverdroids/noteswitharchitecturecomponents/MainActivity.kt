 package com.hoverdroids.noteswitharchitecturecomponents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.hoverdroids.noteswitharchitecturecomponents.viewmodels.NoteViewModel

 class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get a new or existing ViewModel from the ViewModelProvider
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        //Add an observer on the LiveData returned by getAllNotes
        //The onChanged() method fires when the observed data changes and the activity is in the foreground
    }
}