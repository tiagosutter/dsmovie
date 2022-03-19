package com.tiagoexemplo.dsmovie.ui.common.dialogs


import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.tiagoexemplo.dsmovie.R

class SimpleInfoDialog() : DialogFragment(R.layout.meu_dialog) {

    companion object {
        const val MESSAGE = "message"

        fun newInstance(message: String): SimpleInfoDialog {
            val args = Bundle()
            args.putString(MESSAGE, message)

            val fragment = SimpleInfoDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textViewMessage = view.findViewById<TextView>(R.id.textViewMessage)

        val arguments = requireArguments()
        textViewMessage.text = arguments.getString(MESSAGE)
    }
}