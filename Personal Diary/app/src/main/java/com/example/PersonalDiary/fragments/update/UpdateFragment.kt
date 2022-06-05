package com.example.PersonalDiary.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.PersonalDiary.R
import com.example.PersonalDiary.model.Note
import com.example.PersonalDiary.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.fragment_update.view.cancel_btn
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        view.updateFirstName_et.setText(args.currentNote.noteTitle)
        view.updateLastName_et.setText(args.currentNote.note)

        view.update_btn.setOnClickListener {
            updateItem()
        }
        view.cancel_btn.setOnClickListener{
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val noteTitle = updateFirstName_et.text.toString()
        val note = updateLastName_et.text.toString()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date()).toString()
        if (inputCheck(noteTitle)) {
            val updatedNote = Note(args.currentNote.id, noteTitle, note, currentDate)
            mNoteViewModel.updateNote(updatedNote)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Title is required", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(noteTitle: String): Boolean {
        return !(TextUtils.isEmpty(noteTitle))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mNoteViewModel.deleteNote(args.currentNote)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentNote.noteTitle}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentNote.noteTitle}?")
        builder.setMessage("Are you sure you want to delete ${args.currentNote.noteTitle}?")
        builder.create().show()
    }
}