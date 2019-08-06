package com.example.pm3elektrik.TelefonListeSayfasi.TelefonEkleFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment

import com.example.pm3elektrik.R
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonModel.TelefonListeModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_telefon_ekle.*


class TelefonEkle : DialogFragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("MotorListe")
    val telefonModel = TelefonListeModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_telefon_ekle, container, false)

        val buttonEkle = view.findViewById<Button>(R.id.buttonTelefonEkle)
        val buttonClose = view.findViewById<ImageView>(R.id.imgTelefonEkleClose)

        buttonEkle.setOnClickListener {

            val telefonIsim = etTelefonNo.text.toString()
            val telefonNo=etTelefonIsim.text.toString()

            if(telefonIsim.isNotEmpty() && telefonNo.isNotEmpty() ){


                telefonModel.telefonIsim = telefonIsim
                telefonModel.telefonNo = telefonNo

                ref.child("Telefon")
                    .child(telefonNo)
                    .setValue(telefonModel)
                    .addOnCompleteListener {
                        if (it.isComplete){
                            Toast.makeText(activity,"Kayıt Yapıldı",Toast.LENGTH_LONG).show()
                        }else {
                            Toast.makeText(activity,"Kayıt Yapılamadı Hata: ${it.exception?.message}",Toast.LENGTH_LONG).show()

                        }
                    }




            }else {
                Toast.makeText(activity,"Boş Alanları Doldurunuz",Toast.LENGTH_LONG).show()
            }
        }

        buttonClose.setOnClickListener {
            dismiss()
        }

        return view
    }


}
