package ru.laneboy.sportgoversiontwo.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ru.laneboy.sportgoversiontwo.databinding.ProgressBarFullScreenBinding

fun initProgressBar(layoutInflater: LayoutInflater, context: Context): AlertDialog {
    return AlertDialog.Builder(context).apply {
        setView(ProgressBarFullScreenBinding.inflate(layoutInflater).root)
        setCancelable(false)
    }.create().apply {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}


fun Fragment.showToast(message: String) =
    Toast.makeText(this.requireActivity(), message, Toast.LENGTH_SHORT).show()

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}
