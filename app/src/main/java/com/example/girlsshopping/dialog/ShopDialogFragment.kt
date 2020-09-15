package com.example.girlsshopping.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.girlsshopping.R

class ShopDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setIcon(R.mipmap.star_foreground)
        builder.setTitle(R.string.congratulation)
        builder.setMessage(R.string.dialog_message)
        builder.setCancelable(false)
        builder.setNegativeButton(R.string.super_dialog) { dialogInterface, i -> }
        return builder.create()
    }
}