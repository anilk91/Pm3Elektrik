package com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoDuzenleDegistirDialogFR


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoEtiketDuzenle.TrafoGosterDuzenle

import com.example.pm3elektrik.R

class TrafoDuzenleDegistirDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_trafo_duzenle_degistir, container, false)

        val fotoYukle = view.findViewById<Button>(R.id.btnTrafoFotoYukle)
        val ekle = view.findViewById<Button>(R.id.btnTrafoBilgiEkle)
        val degTarihi = view.findViewById<EditText>(R.id.etTrafoDegTarihi)
        val not = view.findViewById<EditText>(R.id.etTrafoNot)

        fotoYukle.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, 100)
        }

        ekle.setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != Activity.RESULT_CANCELED) {
            if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {

                val galeridenGelenResimYolu = data.data

                TrafoGosterDuzenle().gelenResimUri(galeridenGelenResimYolu)
            }
        }
    }
}
