package com.hoverdroids.noteswitharchitecturecomponents

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.RenderScript
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast

class AddNoteActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var priority: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        priority = findViewById(R.id.priority)

        priority.minValue = 1
        priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        setTitle("Add Note")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.save -> {
                saveNote()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun saveNote() {
        val titleText = title.text.toString()
        val descriptionText = description.text.toString()
        val priorityValue = priority.value
        if(titleText.trim().isEmpty() || descriptionText.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_TITLE, titleText)
        intent.putExtra(EXTRA_DESCRIPTION, descriptionText)
        intent.putExtra(EXTRA_PRIORITY, priorityValue)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}