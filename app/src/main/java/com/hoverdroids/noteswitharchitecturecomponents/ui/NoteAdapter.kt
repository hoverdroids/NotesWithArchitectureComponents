package com.hoverdroids.noteswitharchitecturecomponents.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hoverdroids.noteswitharchitecturecomponents.R
import com.hoverdroids.noteswitharchitecturecomponents.database.entities.Note

class NoteAdapter(): ListAdapter<Note, NoteViewHolder>(NoteDiffCallback()) {

    var onItemClickListener: OnItemClickListener? = null

    fun getItemAt(position: Int): Note {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(this, view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.title.text = note.title
        holder.description.text = note.description
        holder.priority.text = note.priority.toString()
    }
}