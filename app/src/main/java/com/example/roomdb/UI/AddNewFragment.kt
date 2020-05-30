package com.example.roomdb.UI

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.roomdb.DB.Note
import com.example.roomdb.DB.NoteDatabase

import com.example.roomdb.R
import kotlinx.android.synthetic.main.fragment_add_new.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */

class AddNewFragment : BaseFragment() {

    // this variable holds edited value from the database
    private var editedNote: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            editedNote= AddNewFragmentArgs.fromBundle(it).note
            title.setText(editedNote?.title)
            editTextNote.setText(editedNote?.note)
        }

        fABSave.setOnClickListener{view->
            val noteTitle = title.text.toString().trim()
            val noteBody = editTextNote.text.toString().trim()

            //validates the input text box
            if(noteTitle.isEmpty()){
                title.error= "Title Required"
                title.requestFocus()
                return@setOnClickListener
            }

            if(noteBody.isEmpty()){
                editTextNote.error= "Title Required"
                editTextNote.requestFocus()
                return@setOnClickListener
            }
            launch {

                context?.let {
                    // it holds new input to be stored in DB
                    val newnote= Note(noteTitle,noteBody)

                    //so if editedNote=null(you did not click to edit value in DB)
                   //uses addNote() from our Dao to save fresh data
                    if (editedNote==null){
                        NoteDatabase(it).getNoteDao().addNote(newnote)
                        it.toast("Note Saved")
                    }else{
                        // editedNote is not null i.e you want to save a value that has just been edited
                        //uses updateNote() from our DAO to update edited data
                        newnote.id = editedNote!!.id
                        NoteDatabase(it).getNoteDao().updateNote(newnote)
                        it.toast("Note Updated")
                    }
                    
                    val action = AddNewFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }
            }


        }
    }


}
