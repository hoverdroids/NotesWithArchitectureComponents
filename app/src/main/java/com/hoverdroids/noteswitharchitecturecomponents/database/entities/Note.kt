package com.hoverdroids.noteswitharchitecturecomponents.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//NOTE: The column info is not required. When not present, the column will be given the same
//name as the property. I'm adding here to highlight the feature
@Entity(tableName = "note_table")
data class Note(
    @ColumnInfo(name="title") var title: String,
    @ColumnInfo(name="description") var description: String = "",
    @ColumnInfo(name="priority") var priority: Int,
    @PrimaryKey(autoGenerate = true) var id: Int = 0//last so that we don't have to pass an ID value or named arguments
)