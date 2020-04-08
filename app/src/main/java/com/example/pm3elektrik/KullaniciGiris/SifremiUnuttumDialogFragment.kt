package com.example.pm3elektrik.KullaniciGiris

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment

import com.example.pm3elektrik.R

class SifremiUnuttumDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragment_dialog_sifremi_unuttum, container, false)

        val closeButton = view.findViewById<ImageView>(R.id.imgSifremiUnuttumClose)
        val sifremiUnuttumYazisi = view.findViewById<TextView>(R.id.tvSifremiUnuttumYazisi)

        closeButton.setOnClickListener {
            dismiss()
        }
        return view
    }

}
