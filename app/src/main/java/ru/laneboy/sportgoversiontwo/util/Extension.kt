package ru.laneboy.sportgoversiontwo.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import ru.laneboy.sportgoversiontwo.databinding.ProgressBarFullScreenBinding

fun initProgressBar(layoutInflater: LayoutInflater, context: Context): AlertDialog {
    return AlertDialog.Builder(context).apply {
        setView(ProgressBarFullScreenBinding.inflate(layoutInflater).root)
        setCancelable(false)
    }.create().apply {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
