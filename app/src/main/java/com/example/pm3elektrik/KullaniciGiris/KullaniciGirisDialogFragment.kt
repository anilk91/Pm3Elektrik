package com.example.pm3elektrik.KullaniciGiris

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.pm3elektrik.AnaSayfa.AnaSayfa
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel

import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class KullaniciGirisDialogFragment : DialogFragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dialog_kullanici_giris, container, false)

        val sicilNo = view.findViewById<EditText>(R.id.etKullaniciGirisSicilNo)
        val sifre = view.findViewById<EditText>(R.id.etKullaniciGirisSifre)
        val girisYap = view.findViewById<Button>(R.id.btnKullaniciGirisYap)
        val sifremiUnuttum = view.findViewById<TextView>(R.id.tvKullaniciSifremiUnuttum)
        val closeButton = view.findViewById<ImageView>(R.id.imgKullaniciGirisClose)

        girisYap.setOnClickListener {
            if (sicilNo.text.toString().isNotEmpty() && sifre.text.toString().isNotEmpty()){
                Log.e("sicil","${sicilNo.text.toString()}")
                Log.e("sifre","${sifre.text.toString()}")
                ref.orderByKey().addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {

                        for(okunan in p0.children){

                            val gelenBilgiler = okunan.getValue(KullaniciModel::class.java)

                            if (gelenBilgiler?.sicilNo.toString() == sicilNo.text.toString()){
                                if (gelenBilgiler?.sifre.toString() == sifre.text.toString()){
                                    val sharedPreferences = activity?.getSharedPreferences("gelenKullaniciBilgileri", 0)
                                    val editor = sharedPreferences?.edit()
                                    editor?.putString("KEY_GELEN_SICIL_NO", gelenBilgiler?.sicilNo.toString())
                                    Toast.makeText(context?.applicationContext,"Giriş Başarılı",Toast.LENGTH_SHORT).show()
                                    anaSayfayaGec()
                                }else{
                                    Toast.makeText(context?.applicationContext,"Girilen Şifre Yanlış",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }


                })

            }else{
                Toast.makeText(context?.applicationContext,"Boş Alanları Doldurunuz",Toast.LENGTH_SHORT).show()
            }
        }

        sifremiUnuttum.setOnClickListener {
            val sifremiUnuttumSayfasi = SifremiUnuttumDialogFragment()
            sifremiUnuttumSayfasi.show(fragmentManager!!,"sifremi_unuttum_dialog_fr")
        }

        closeButton.setOnClickListener {
            dismiss()
        }
        return view
    }

    private fun anaSayfayaGec(){

        val intent = Intent(context?.applicationContext,AnaSayfa::class.java)
        activity?.startActivity(intent)
        dismiss()

    }

}