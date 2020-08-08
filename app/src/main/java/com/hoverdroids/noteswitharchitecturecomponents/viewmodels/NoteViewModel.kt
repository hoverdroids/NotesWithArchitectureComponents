package com.hoverdroids.noteswitharchitecturecomponents.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/** View Model to keep a reference to the note repository and an up-to-date list of all notes. **/
class NoteViewModel(application: Application): AndroidViewModel(application) {
}