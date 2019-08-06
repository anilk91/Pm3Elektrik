package com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitEkle


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitModel.AmbarKayitModeli

import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase

class AmbarKayitEkleme : DialogFragment() {

    val ambarListeEkle = AmbarKayitModeli()
    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ambar_kayit_ekleme, container, false)

        val close = view.findViewById<ImageView>(R.id.imgAmbarEkleClose)
        val ekle = view.findViewById<Button>(R.id.buttonAmbarEkle)

        close.setOnClickListener {
            dismiss()
        }
        ekle.setOnClickListener {

            val stokNo=view.findViewById<EditText>(R.id.etAmbarStokNo).text.toString()
            val rafNo=view.findViewById<EditText>(R.id.etAmbarRafNo).text.toString()
            val tanim=view.findViewById<EditText>(R.id.etAmbarTanim).text.toString()

            if(stokNo.isNotEmpty() && rafNo.isNotEmpty() && tanim.isNotEmpty()){

                ambarListeEkle.ambarStokNo = stokNo
                ambarListeEkle.ambarRafNo = rafNo
                ambarListeEkle.ambarTanim = tanim

                ref.child("Ambar")
                    .child(stokNo)
                    .setValue(ambarListeEkle)
                    .addOnCompleteListener {

                        if(it.isComplete){
                            Toast.makeText(activity,"Kayıt Yapıldı",Toast.LENGTH_LONG).show()
                        }else {
                            Toast.makeText(activity,"Kayıt Yapılamadı Hata:${it.exception?.message}",Toast.LENGTH_LONG).show()
                        }
                    }


            }else {
                Toast.makeText(activity,"Boş Alanları Doldurunuz",Toast.LENGTH_LONG).show()
            }
        }

        return view
    }


}
