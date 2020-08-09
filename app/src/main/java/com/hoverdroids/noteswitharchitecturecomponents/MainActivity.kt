 package com.hoverdroids.noteswitharchitecturecomponents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hoverdroids.noteswitharchitecturecomponents.database.entities.Note
import com.hoverdroids.noteswitharchitecturecomponents.ui.NoteAdapter
import com.hoverdroids.noteswitharchitecturecomponents.viewmodels.NoteViewModel

 class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FloatingActionButton>(R.id.add_note).setOnClickListener(this)

        val adapter = NoteAdapter()

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter

        //Get a new or existing ViewModel from the ViewModelProvider
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        //Add an observer on the LiveData returned by getAllNotes
        //The onChanged() method fires when the observed data changes and the activity is in the foreground
        noteViewModel.allNotes.observe(this, Observer { notes ->
            // Update the cached copy of the words in the adapter.
            notes?.let { adapter.notes = notes }
        })
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

         if(requestCode == INTENT_REQUEST_ADD_NOTE && resultCode == RESULT_OK) {
             data?.let {
                 val titleText = data.getStringExtra(EXTRA_TITLE) ?: ""
                 val descriptionText = data.getStringExtra(EXTRA_DESCRIPTION) ?: ""
                 val priorityValue = data.getIntExtra(EXTRA_PRIORITY, 1)

                 val note = Note(-1, titleText, descriptionText, priorityValue)
                 noteViewModel.insert(note)

                 Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
             }
         } else {
             Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show()
         }
     }

     override fun onClick(view: View) {
         val intent = Intent(this, AddNoteActivity::class.java)
         startActivityForResult(intent, INTENT_REQUEST_ADD_NOTE)
     }
 }