package com.hoverdroids.noteswitharchitecturecomponents.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hoverdroids.noteswitharchitecturecomponents.R
import com.hoverdroids.noteswitharchitecturecomponents.database.entities.Note

class NoteAdapter: RecyclerView.Adapter<NoteViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    var notes = listOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()//NOT EFFICIENT! NOT ANIMATED!
        }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun getItemAt(position: Int): Note {
        return notes[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(this, view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.title.text = note.title
        holder.description.text = note.description
        holder.priority.text = note.priority.toString()
    }
}