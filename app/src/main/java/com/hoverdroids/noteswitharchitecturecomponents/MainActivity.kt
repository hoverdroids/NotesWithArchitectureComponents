 package com.hoverdroids.noteswitharchitecturecomponents

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hoverdroids.noteswitharchitecturecomponents.database.entities.Note
import com.hoverdroids.noteswitharchitecturecomponents.ui.NoteAdapter
import com.hoverdroids.noteswitharchitecturecomponents.ui.NoteItemTouchHandler
import com.hoverdroids.noteswitharchitecturecomponents.ui.OnItemClickListener
import com.hoverdroids.noteswitharchitecturecomponents.viewmodels.NoteViewModel

 class MainActivity : AppCompatActivity(), View.OnClickListener, OnItemClickListener {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FloatingActionButton>(R.id.add_note).setOnClickListener(this)

        val adapter = NoteAdapter()
        adapter.onItemClickListener = this

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter

        //Get a new or existing ViewModel from the ViewModelProvider
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        //Add an observer on the LiveData returned by getAllNotes
        //The onChanged() method fires when the observed data changes and the activity is in the foreground
        noteViewModel.getAllNotes().observe(this, Observer { notes ->
            // Update the cached copy of the words in the adapter.
            notes?.let { adapter.submitList(notes) }
        })

        ItemTouchHelper(NoteItemTouchHandler(noteViewModel, adapter)).attachToRecyclerView(recycler)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when{
            requestCode == INTENT_REQUEST_ADD_NOTE && resultCode == RESULT_OK && data != null -> {
                val titleText = data.getStringExtra(EXTRA_TITLE) ?: ""
                val descriptionText = data.getStringExtra(EXTRA_DESCRIPTION) ?: ""
                val priorityValue = data.getIntExtra(EXTRA_PRIORITY, 1)

                val note = Note(titleText, descriptionText, priorityValue)
                noteViewModel.insert(note)

                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
            }
            requestCode == INTENT_REQUEST_EDIT_NOTE && resultCode == RESULT_OK && data != null -> {
                val id = data.getIntExtra(EXTRA_ID, -1)
                if(id == -1) {
                    Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()
                    return
                }

                val title = data.getStringExtra(EXTRA_TITLE) ?: ""
                val description = data.getStringExtra(EXTRA_DESCRIPTION) ?: ""
                val priority = data.getIntExtra(EXTRA_PRIORITY, 1)
                noteViewModel.update(Note(title, description, priority, id))

                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
            }
            else -> Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show()
        }
    }

     override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         return when(item.itemId) {
             R.id.delete_all_notes -> {
                 noteViewModel.deleteAll()
                 Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                 return true
             }
             else -> {
                 super.onOptionsItemSelected(item)
             }
         }
     }

     override fun onClick(view: View) {
         val intent = Intent(this, AddEditNoteActivity::class.java)
         startActivityForResult(intent, INTENT_REQUEST_ADD_NOTE)
     }

     override fun onItemClick(note: Note) {
         val intent = Intent(this, AddEditNoteActivity::class.java)
         intent.putExtra(EXTRA_ID, note.id)
         intent.putExtra(EXTRA_TITLE, note.title)
         intent.putExtra(EXTRA_DESCRIPTION, note.description)
         intent.putExtra(EXTRA_PRIORITY, note.priority)
         startActivityForResult(intent, INTENT_REQUEST_EDIT_NOTE)
     }
 }