package com.tiagoexemplo.dsmovie


import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class MeuDialogFragment() : DialogFragment(R.layout.meu_dialog) {

    companion object {
        const val MESSAGE = "message"

        fun newInstance(message: String): MeuDialogFragment {
            val args = Bundle()
            args.putString(MESSAGE, message)

            val fragment = MeuDialogFragment()
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