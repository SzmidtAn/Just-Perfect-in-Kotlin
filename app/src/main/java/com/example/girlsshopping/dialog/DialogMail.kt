package com.example.girlsshopping.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.girlsshopping.R

class DialogMail : DialogFragment() {
    var setMessage = 0
    var mesageText = 0
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setMessage = R.string.message
        mesageText = R.string.messageText
        val builder = AlertDialog.Builder(activity)
        builder.setIcon(R.mipmap.star_foreground)
        builder.setTitle(setMessage)
        builder.setMessage(mesageText)
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.send) { dialog, which -> Toast.makeText(context, R.string.message_correct, Toast.LENGTH_SHORT).show() }
        builder.setNegativeButton(R.string.cancel) { dialogInterface, i -> }
        return builder.create()
    }
}