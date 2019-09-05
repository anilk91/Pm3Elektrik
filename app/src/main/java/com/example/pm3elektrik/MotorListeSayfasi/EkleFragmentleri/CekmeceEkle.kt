package com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel

import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase

class CekmeceEkle : Fragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Motor")
    val cekmeceListe = MotorModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cekmece_ekle, container, false)

        val close = view.findViewById<ImageView>(R.id.imgCekmeceEkleClose)
        val buttonEkle = view.findViewById<Button>(R.id.btnCekmeceSalterEkle)

        close.setOnClickListener {
            changeFragment(MotorListe())
        }

        buttonEkle.setOnClickListener {

            val isim = view.findViewById<EditText>(R.id.etCekmeceIsim).text.toString().toUpperCase()
            val marka = view.findViewById<EditText>(R.id.etCekmeceSalterIsmi).text.toString().toUpperCase()
            val model = view.findViewById<EditText>(R.id.etCekmeceSalterModel).text.toString().toUpperCase()
            val kapasite = view.findViewById<EditText>(R.id.etCekmeceSalterKapasite).text.toString()
            val cat = view.findViewById<EditText>(R.id.etCekmeceSalterCat).text.toString().toUpperCase()
            val degTarihi = view.findViewById<EditText>(R.id.etCekmeceSalterDegTarihi).text.toString()
            val demeraj = view.findViewById<EditText>(R.id.etCekmeceSalterDemeraj).text.toString().toUpperCase()
            val mccYeri = view.findViewById<EditText>(R.id.etCekmeceMccYeri).text.toString().toUpperCase()

            if (!isim.isNullOrEmpty()){
                firebaseDBEkle(isim,marka,model,kapasite,cat,degTarihi,demeraj,mccYeri)
            }else{
                Toast.makeText(view.context,"Çekmece İsmini Giriniz!",Toast.LENGTH_SHORT).show()
            }

            Toast.makeText(view.context,"Kayıt Başarılı",Toast.LENGTH_SHORT).show()

        }



        return view
    }

    private fun firebaseDBEkle(isim: String, marka: String, model: String, kapasite: String, cat: String, degTarihi: String, demeraj: String , mccYeri : String) {

        cekmeceListe.cekmeceIsim = isim
        cekmeceListe.cekmeceMarka = marka
        cekmeceListe.cekmeceModel = model
        cekmeceListe.cekmeceKapasite = kapasite
        cekmeceListe.cekmeceCat = cat
        cekmeceListe.cekmeceSalterDegisim = degTarihi
        cekmeceListe.cekmeceDemeraj = demeraj

        cekmeceListe.motorMCCYeri = mccYeri
        cekmeceListe.motorTag = isim
        cekmeceListe.motorGucKW = 1500000.0
        cekmeceListe.motorDevir = "Sadece Salter"


        ref.push()
            .setValue(cekmeceListe)
            .addOnCompleteListener { it->

                if(it.isComplete){

                }else{
                    try{
                        Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }catch (hata:Exception){ }
                }
            }
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"cekmece_ekle_fr")
        fragmentTransaction.commit()

    }
}
