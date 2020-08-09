package com.hoverdroids.noteswitharchitecturecomponents.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hoverdroids.noteswitharchitecturecomponents.R

class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var title = view.findViewById<TextView>(R.id.title)
    var description = view.findViewById<TextView>(R.id.description)
    var priority = view.findViewById<TextView>(R.id.priority)
}