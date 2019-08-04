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

class MotorEkle : Fragment() {

    val motor_liste = MotorModel()
    val ref = FirebaseDatabase.getInstance().reference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_motor_ekle, container, false)

        val button_close = view.findViewById<ImageView>(R.id.imgMotorCLose)
        val button_ekle = view.findViewById<Button>(R.id.buttonMotorEkle)

        button_ekle.setOnClickListener {

            val motor_tag = view.findViewById<EditText>(R.id.etSalterMotorTag).text.toString()
            val guc_kw = view.findViewById<EditText>(R.id.etGucKw).text.toString()
            val guc_hp = view.findViewById<EditText>(R.id.etGucHP).text.toString()
            val devir = view.findViewById<EditText>(R.id.etDevir).text.toString()
            val nom_trip_akimi = view.findViewById<EditText>(R.id.etNomTripAkimi).text.toString()
            val insa_tipi = view.findViewById<EditText>(R.id.etInsaTipi).text.toString()
            val flans = view.findViewById<EditText>(R.id.etFlans).text.toString()
            val adres = view.findViewById<EditText>(R.id.etMotorAdres).text.toString()
            val mcc_yeri = view.findViewById<EditText>(R.id.etMotorMCCYeri).text.toString()
            val degisim_tarihi = view.findViewById<EditText>(R.id.etMotorDegTarihi).text.toString()


            if (motor_tag.isNotEmpty() && mcc_yeri.isNotEmpty()){

                    FirebaseDBMotorEkle(motor_tag, guc_kw, guc_hp,devir,nom_trip_akimi,insa_tipi,flans,adres,mcc_yeri,degisim_tarihi)

            }else{
                Toast.makeText(activity,"Lütfen Motor Tag ve Mcc Yerini Giriniz",Toast.LENGTH_LONG).show()
            }
        }

        button_close.setOnClickListener {
        changeFragment(MotorListe())
        }
        return view
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"fragment")
        fragmentTransaction.commit()

    }

    fun FirebaseDBMotorEkle(motorTag: String, motorGucKW: String, motorGucHP: String, motorDevir: String, motorNomTripAkimi: String,
                  motorInsaTipi: String, motorFlans: String, motorAdres: String, motorMCCYeri: String, motorDegTarihi: String){


        motor_liste.motorTag = motorTag
        motor_liste.motorDevir = motorDevir
        motor_liste.motorNomTripAkimi = motorNomTripAkimi
        motor_liste.motorInsaTipi = motorInsaTipi
        motor_liste.motorFlans = motorFlans
        motor_liste.motorAdres = motorAdres
        motor_liste.motorMCCYeri = motorMCCYeri
        motor_liste.motorDegTarihi = motorDegTarihi


        if(motorGucKW.isEmpty() && motorGucHP.isEmpty()){

            motor_liste.motorGucKW = motorGucKW
            motor_liste.motorGucHP = motorGucHP

        }else if (motorGucKW.isEmpty()){

            val hp = motorGucHP.toDouble()
            val kw_karsiligi = (0.75*hp)
            motor_liste.motorGucKW ="$kw_karsiligi"
            motor_liste.motorGucHP = motorGucHP

        }else if(motorGucHP.isEmpty()){

            val kw = motorGucKW.toDouble()
            val hp_karsiligi = (kw/0.75)
            motor_liste.motorGucHP ="$hp_karsiligi"
            motor_liste.motorGucKW = motorGucKW

        }else {
            motor_liste.motorGucKW = motorGucKW
            motor_liste.motorGucHP = motorGucHP
        }
        ref.child("MotorListe")
            .child(motorTag)
            .child("Motor")
            .setValue(motor_liste).addOnCompleteListener {

                if(it.isSuccessful){
                    Toast.makeText(activity,"Kayıt Yapıldı", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity,"Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


}
