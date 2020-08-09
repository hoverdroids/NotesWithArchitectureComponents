package com.hoverdroids.noteswitharchitecturecomponents.ui

import com.hoverdroids.noteswitharchitecturecomponents.database.entities.Note

interface OnItemClickListener {
    fun onItemClick(note: Note)
}