package com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SalterModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SurucuModel

import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat


class MotorEkle : Fragment() {

    val motor_liste = MotorModel()
    val salterListe = SalterModel()
    val surucuListe = SurucuModel()
    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")

    companion object{

        var gucKW_static = 0.0

    }

    interface motorEkledenGiden {
        fun motorEkledenGidenVeri (motorTag: String, motorMCCYeri: String, motorGucKW: Double, motorDevir: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_motor_ekle, container, false)

        val button_close = view.findViewById<ImageView>(R.id.imgMotorCLose)
        val button_ekle = view.findViewById<Button>(R.id.buttonMotorEkle)

        val gucKw = view.findViewById<EditText>(R.id.etGucKw)
        val gucHp = view.findViewById<EditText>(R.id.etGucHP)

        gucKw.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {

                if(!p0.isNullOrBlank()){
                    val kw = gucKw.text.toString().toDouble()
                    val hp_karsiligi = DecimalFormat("##.#").format(kw/0.75)
                    motor_liste.motorGucHP = hp_karsiligi.toDouble()
                    motor_liste.motorGucKW = gucKw.text.toString().toDouble()
                    gucKW_static = kw

                }

            }
        })

        gucHp.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {

                if(!p0.isNullOrEmpty()){
                    val hp = gucHp.text.toString().toDouble()
                    val kw_karsiligi =  DecimalFormat("##.#").format(0.75*hp)
                    motor_liste.motorGucKW = kw_karsiligi.toDouble()
                    motor_liste.motorGucHP = gucHp.text.toString().toDouble()

                    gucKW_static = kw_karsiligi.toDouble()

                }
            }
        })



        button_ekle.setOnClickListener {

            val motor_isim = view.findViewById<EditText>(R.id.etMotorIsim).text.toString().toUpperCase()
            val motor_tag = view.findViewById<EditText>(R.id.etMotorTag).text.toString().toUpperCase()
            val devir = view.findViewById<EditText>(R.id.etDevir).text.toString().toUpperCase()
            val nom_trip_akimi = view.findViewById<EditText>(R.id.etNomTripAkimi).text.toString().toUpperCase()
            val insa_tipi = view.findViewById<EditText>(R.id.etInsaTipi).text.toString().toUpperCase()
            val flans = view.findViewById<EditText>(R.id.etFlans).text.toString().toUpperCase()
            val adres = view.findViewById<EditText>(R.id.etMotorAdres).text.toString().toUpperCase()
            val mcc_yeri = view.findViewById<EditText>(R.id.etMotorMCCYeri).text.toString().toUpperCase()
            val degisim_tarihi = view.findViewById<EditText>(R.id.etMotorDegTarihi).text.toString().toUpperCase()




            if (motor_tag.isNotEmpty() && mcc_yeri.isNotEmpty()){


//                (view.context as motorEkledenGiden).motorEkledenGidenVeri(motor_tag,mcc_yeri, gucKW_static,devir)
//                Log.e("ekleInterface","$gucKW_static")


                    FirebaseDBMotorEkle(motor_isim ,motor_tag,devir,nom_trip_akimi,insa_tipi,flans,adres,mcc_yeri,degisim_tarihi)

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
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"motor_ekle_fr")
        fragmentTransaction.commit()

    }

    fun FirebaseDBMotorEkle(motorIsim : String , motorTag: String, motorDevir: String, motorNomTripAkimi: String,
                  motorInsaTipi: String, motorFlans: String, motorAdres: String, motorMCCYeri: String, motorDegTarihi: String){

        motor_liste.motorIsim = motorIsim
        motor_liste.motorTag = motorTag
        motor_liste.motorDevir = motorDevir
        motor_liste.motorNomTripAkimi = motorNomTripAkimi
        motor_liste.motorInsaTipi = motorInsaTipi
        motor_liste.motorFlans = motorFlans
        motor_liste.motorAdres = motorAdres
        motor_liste.motorMCCYeri = motorMCCYeri
        motor_liste.motorDegTarihi = motorDegTarihi

        salterListe.salterMotorTag = motorTag
        salterListe.salterMccYeri = motorMCCYeri
        salterListe.salterMarka = ""
        salterListe.salterDegTarihi = ""
        salterListe.salterDemeraj = ""
        salterListe.salterSTYLE = ""
        salterListe.salterCAT = ""
        salterListe.salterKapasite = ""

        surucuListe.surucuIsim = ""
        surucuListe.surucuDegTarihi = ""
        surucuListe.surucuModel = ""
        surucuListe.surucuDIPSivic = ""
        surucuListe.surucuBoyut = ""

        ref.child("Motor")
            .child(motorTag)
            .setValue(motor_liste).addOnCompleteListener {

                if(it.isSuccessful){
                    Toast.makeText(activity,"Kayıt Yapıldı", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity,"Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

        ref.child("Salter")
            .child(motorTag)
            .setValue(salterListe).addOnCompleteListener {
                if(it.isSuccessful){

                }else{
                    Toast.makeText(activity,"Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

        ref.child("Surucu")
            .child(motorTag)
            .setValue(surucuListe).addOnCompleteListener {
                if(it.isSuccessful){

                }else{
                    Toast.makeText(activity,"Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
