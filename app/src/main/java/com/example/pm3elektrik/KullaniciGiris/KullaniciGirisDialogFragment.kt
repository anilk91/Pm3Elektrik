package com.example.pm3elektrik.KullaniciGiris

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.pm3elektrik.AnaSayfa.AnaSayfa
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel

import com.example.pm3elektrik.R
import com.example.pm3elektrik.YoneticiSayfasi.YoneticiAnaSayfa
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class KullaniciGirisDialogFragment(gelenContext: KullaniciGirisSicilveIsim) : DialogFragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar")
    var mContext = gelenContext

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dialog_kullanici_giris, container, false)

        val sicilNo = view.findViewById<EditText>(R.id.etKullaniciGirisSicilNo)
        val sifre = view.findViewById<EditText>(R.id.etKullaniciGirisSifre)
        val girisYap = view.findViewById<Button>(R.id.btnKullaniciGirisYap)
        val sifremiUnuttum = view.findViewById<TextView>(R.id.tvKullaniciSifremiUnuttum)
        val closeButton = view.findViewById<ImageView>(R.id.imgKullaniciGirisClose)

        girisYap.setOnClickListener {
            if (sicilNo.text.toString().isNotEmpty() && sifre.text.toString().isNotEmpty()){

                ref.orderByKey().addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {

                        for(okunan in p0.children){

                            val gelenBilgiler = okunan.getValue(KullaniciModel::class.java)

                            if (gelenBilgiler?.sicilNo.toString() == sicilNo.text.toString()){
                                if (gelenBilgiler?.sifre.toString() == sifre.text.toString()){
                                    if (gelenBilgiler?.sicilNo == 1111){
                                        yoneticiSayfasinaGec()
                                    }else{
                                        val sharedPreferences = activity?.getSharedPreferences("gelenKullaniciIsmi", 0)
                                        val editor = sharedPreferences?.edit()
                                        editor?.putInt("KEY_SICIL_NO", gelenBilgiler!!.sicilNo)
                                        editor?.commit()
                                        Toast.makeText(mContext,"Giriş Başarılı",Toast.LENGTH_SHORT).show()
                                        anaSayfayaGec()
                                    }
                                }else{
                                    Toast.makeText(mContext,"Girilen Şifre Yanlış",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }


                })

            }else{
                Toast.makeText(mContext,"Boş Alanları Doldurunuz",Toast.LENGTH_SHORT).show()
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

        val intent = Intent(mContext,AnaSayfa::class.java)
        mContext.startActivity(intent)
        mContext.finish()
        dismiss()

    }

    private fun yoneticiSayfasinaGec(){


        val intent = Intent(mContext,YoneticiAnaSayfa::class.java)
        mContext.startActivity(intent)
        mContext.finish()
        dismiss()

    }

}
