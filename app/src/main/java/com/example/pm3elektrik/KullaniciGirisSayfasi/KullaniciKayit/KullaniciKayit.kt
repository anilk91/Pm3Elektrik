package com.example.pm3elektrik.KullaniciGirisSayfasi.KullaniciKayit


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pm3elektrik.KullaniciGirisSayfasi.KullaniciModel.KullaniciModel

import com.example.pm3elektrik.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_kullanici_kayit.*

class KullaniciKayit : Fragment() {

    var kullaniciModel = KullaniciModel()
    private lateinit var mAuth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kullanici_kayit, container, false)

        val isim = view.findViewById<EditText>(R.id.etKullaniciKayitIsim).text
        val sicilNo = view.findViewById<EditText>(R.id.etKullaniciKayitSicilNo).text
        val mailAdres = view.findViewById<EditText>(R.id.etKullaniciKayitMailAdres).text
        val sifre = view.findViewById<EditText>(R.id.etKullaniciKayitSifre).text
        val sifreTekrar = view.findViewById<EditText>(R.id.etKullaniciKayitSifreTekrar).text
        val kayitOl = view.findViewById<Button>(R.id.btnKullaniciKayitOl)

        mAuth = FirebaseAuth.getInstance()


        kayitOl.setOnClickListener {

            Log.e("deneme","${isim.toString()} ${sicilNo.toString()} ${mailAdres.toString()} ${sifre.toString()} ${sifreTekrar.toString()}")
            if (isim.isNotEmpty() && sicilNo.isNotEmpty() && mailAdres.isNotEmpty() && sifre.isNotEmpty() && sifreTekrar.isNotEmpty()){

                if (sifre.toString().equals(sifreTekrar.toString())){


                    mAuth.createUserWithEmailAndPassword(mailAdres.toString(),sifre.toString()).addOnCompleteListener {

                        if (it.isSuccessful){
                            Toast.makeText(view.context, "Kayıt Yapıldı Mail Adresinizi Onaylayınız", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(view.context, "Kayıt Yapılamadı Hata: ${it.exception}", Toast.LENGTH_SHORT).show()
                            Log.e("HATA","${it.exception}")
                        }
                        kullaniciModel.isim = isim.toString()
                        kullaniciModel.sicilNo = sicilNo.toString().toInt()
                        kullaniciModel.kullaniciUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
                        kullaniciModel.mailAdres = mailAdres.toString()

                        FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                            .child("Kullanicilar").child("${sicilNo.toString().toInt()}")
                            .setValue(kullaniciModel)

                    }
                }else{
                    Toast.makeText(view.context,"Şifreler Uyuşmuyor",Toast.LENGTH_LONG).show()
                }

            }else {
                Toast.makeText(view.context,"Boş Alanların Tümünü Doldurunuz",Toast.LENGTH_LONG).show()
            }

        }

        return view
    }


}
