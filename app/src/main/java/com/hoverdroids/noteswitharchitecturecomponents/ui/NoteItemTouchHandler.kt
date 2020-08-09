package com.hoverdroids.noteswitharchitecturecomponents.ui

import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hoverdroids.noteswitharchitecturecomponents.viewmodels.NoteViewModel

class NoteItemTouchHandler(val noteViewModel: NoteViewModel, val adapter: NoteAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT + ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        noteViewModel.delete(adapter.getItemAt(viewHolder.adapterPosition))
    }
}