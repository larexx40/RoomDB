package com.example.roomdb.UI

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.roomdb.DB.Note
import com.example.roomdb.DB.NoteDatabase

import com.example.roomdb.R
import kotlinx.android.synthetic.main.fragment_add_new.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class AddNewFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fABSave.setOnClickListener{
            val noteTitle = title.text.toString().trim()
            val noteBody = note.text.toString().trim()

            if(noteTitle.isEmpty()){
                title.error= "Title Required"
                title.requestFocus()
                return@setOnClickListener
            }

            if(noteBody.isEmpty()){
                note.error= "Title Required"
                note.requestFocus()
                return@setOnClickListener
            }
            launch {
                val note= Note(noteTitle,noteBody)

                context?.let {
                    NoteDatabase(it).getNoteDao().addNote(note)
                    it.toast("NOte Saved")
                }
            }


        }
    }


}
