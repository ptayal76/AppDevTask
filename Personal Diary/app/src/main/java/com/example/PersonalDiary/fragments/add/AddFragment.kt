package com.example.PersonalDiary.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.PersonalDiary.R
import com.example.PersonalDiary.model.Note
import com.example.PersonalDiary.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }
        view.cancel_btn.setOnClickListener{
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        }

        return view
    }

    private fun insertDataToDatabase() {
        val firstName = addFirstName_et.text.toString()
        val lastName = addLastName_et.text.toString()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date()).toString()
        if(inputCheck(firstName)){
            val note = Note(
                0,
                firstName,
                lastName,
                currentDate
            )
            mNoteViewModel.addNote(note)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Title is required", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String): Boolean{
        return !(TextUtils.isEmpty(firstName))
    }

}