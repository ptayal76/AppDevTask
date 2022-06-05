package com.example.PersonalDiary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class  MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setupActionBarWithNavController(findNavController(R.id.fragment))
    }
    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        closeKeyBoard()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
