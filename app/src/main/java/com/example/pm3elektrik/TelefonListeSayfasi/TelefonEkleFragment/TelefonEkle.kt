package com.example.pm3elektrik.TelefonListeSayfasi.TelefonEkleFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.example.pm3elektrik.R
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonListesi
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonModel.TelefonListeModel
import com.google.firebase.database.FirebaseDatabase


class TelefonEkle : DialogFragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
    val telefonModel = TelefonListeModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_telefon_ekle, container, false)

        val buttonEkle = view.findViewById<Button>(R.id.buttonTelefonEkle)
        val buttonClose = view.findViewById<ImageView>(R.id.imgTelefonEkleClose)

        val bundle :Bundle? = arguments
        val isim = bundle?.getString("rvGidenTelIsim")
        val no = bundle?.getString("rvGidenTelNo")

        val telefonNo = view.findViewById<EditText>(R.id.etTelefonNo)
        val telefonIsim = view.findViewById<EditText>(R.id.etTelefonIsim)
        telefonIsim.setText(isim)
        telefonNo.setText(no)

        buttonEkle.setOnClickListener {

            telefonIsim.text.toString().toUpperCase()
            telefonNo.text.toString()

            if(telefonIsim.text.isNotEmpty() && telefonNo.text.isNotEmpty() ){

                TelefonListesi().telefonEkledenGelen(telefonIsim.text.toString().toUpperCase(),telefonNo.text.toString(),view,view.context)

                telefonModel.telefonIsim = telefonIsim.text.toString().toUpperCase()
                telefonModel.telefonNo = telefonNo.text.toString()

                changeFragment(TelefonListesi())

                ref.child("Telefon")
                    .child(telefonNo.text.toString())
                    .setValue(telefonModel)
                    .addOnCompleteListener {
                        if (it.isComplete){

                        }else {
                            try {
                                Toast.makeText(activity,"Kayıt Yapılamadı Hata: ${it.exception?.message}",Toast.LENGTH_LONG).show()
                            }catch (hata : Exception){
                                Toast.makeText(activity,"Hata: ${hata.message}",Toast.LENGTH_LONG).show()
                            }


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

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerTelefonListesi,fragment,"motor_ekle_fr")
        fragmentTransaction.commit()

    }
}
