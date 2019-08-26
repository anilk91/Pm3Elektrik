package com.example.pm3elektrik.MotorListeSayfasi.DuzenleFragmentleri

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_motor_ekle.*
import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode
import com.example.pm3elektrik.MotorListeSayfasi.MotorInterface.MotorEkleInterface as MotorEkleInterface

class MotorEtiketDuzenle : Fragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Motor")
    val motor_liste = MotorModel()

    companion object{
        var gucKW_static = 0.0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_motor_ekle, container, false)


        val button_close = view.findViewById<ImageView>(R.id.imgMotorCLose)
        val button_ekle = view.findViewById<Button>(R.id.buttonMotorEkle)

        val bundle = arguments
        val gelenMotorTag = bundle!!.getString("motor_tag")

        fireBaseOkunanVeriler(gelenMotorTag!!)

        val gucKw = view.findViewById<EditText>(R.id.etGucKw)
        val gucHp = view.findViewById<EditText>(R.id.etGucHP)

        gucKw.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {

                if(!p0.isNullOrBlank()){
                    val kw = gucKw.text.toString().toDouble()
                    val hp = (kw/0.75)
                    val hp_karsiligi = BigDecimal(hp).setScale(1,RoundingMode.HALF_EVEN)
                    motor_liste.motorGucHP = hp_karsiligi.toDouble()
                    motor_liste.motorGucKW = kw
                    gucKW_static = kw
                }

            }
        })

        gucHp.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {

               if(!p0.isNullOrEmpty()){
                   val hp = gucHp.text.toString().toDouble()
                   val kw = hp*0.75
                   val kw_karsiligi =  BigDecimal(kw).setScale(1, RoundingMode.HALF_EVEN)
                   motor_liste.motorGucKW = kw_karsiligi.toDouble()
                   motor_liste.motorGucHP = hp

                   gucKW_static = kw_karsiligi.toDouble()
               }
            }
        })

        button_ekle.setOnClickListener {

            if (etMotorTag.text.toString().isNotEmpty() && etMotorMCCYeri.text.toString().isNotEmpty()) {

                val motor_isim = view.findViewById<EditText>(R.id.etMotorIsim).text.toString().toUpperCase()
                val motor_tag = view.findViewById<EditText>(R.id.etMotorTag).text.toString().toUpperCase()
                val devir = view.findViewById<EditText>(R.id.etDevir).text.toString().toUpperCase()
                val nom_trip_akimi = view.findViewById<EditText>(R.id.etNomTripAkimi).text.toString().toUpperCase()
                val insa_tipi = view.findViewById<EditText>(R.id.etInsaTipi).text.toString().toUpperCase()
                val flans = view.findViewById<EditText>(R.id.etFlans).text.toString().toUpperCase()
                val adres = view.findViewById<EditText>(R.id.etMotorAdres).text.toString().toUpperCase()
                val mcc_yeri = view.findViewById<EditText>(R.id.etMotorMCCYeri).text.toString().toUpperCase()
                val degisim_tarihi = view.findViewById<EditText>(R.id.etMotorDegTarihi).text.toString().toUpperCase()

                FirebaseDBMotorEkle(motor_isim ,motor_tag,devir,nom_trip_akimi,insa_tipi,flans,adres,mcc_yeri,degisim_tarihi)

            } else {
                Toast.makeText(activity, "Lütfen Motor Tag ve Mcc Yerini Giriniz", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    private fun fireBaseOkunanVeriler(gelenMotorTag: String) {
        ref.child(gelenMotorTag)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {

                    val okunan = p0.getValue(MotorModel::class.java)
                    etMotorIsim.setText(okunan!!.motorIsim)
                    etMotorTag.setText(okunan.motorTag)
                    etGucKw.setText("${okunan.motorGucKW}")
                    etGucHP.setText("${okunan.motorGucHP}")
                    etDevir.setText(okunan.motorDevir)
                    etNomTripAkimi.setText(okunan.motorNomTripAkimi)
                    etInsaTipi.setText(okunan.motorInsaTipi)
                    etFlans.setText(okunan.motorFlans)
                    etMotorAdres.setText(okunan.motorAdres)
                    etMotorMCCYeri.setText(okunan.motorMCCYeri)
                    etMotorDegTarihi.setText(okunan.motorDegTarihi)

                }

            })
    }

    fun FirebaseDBMotorEkle(motorIsim: String, motorTag: String, motorDevir: String, motorNomTripAkimi: String,
        motorInsaTipi: String, motorFlans: String, motorAdres: String, motorMCCYeri: String, motorDegTarihi: String) {

        motor_liste.motorIsim = motorIsim
        motor_liste.motorTag = motorTag
        motor_liste.motorDevir = motorDevir
        motor_liste.motorNomTripAkimi = motorNomTripAkimi
        motor_liste.motorInsaTipi = motorInsaTipi
        motor_liste.motorFlans = motorFlans
        motor_liste.motorAdres = motorAdres
        motor_liste.motorMCCYeri = motorMCCYeri
        motor_liste.motorDegTarihi = motorDegTarihi

        ref.child(motorTag)
            .setValue(motor_liste).addOnCompleteListener {

                if (it.isSuccessful) {
                    try {
                        Toast.makeText(activity, "Kayıt Yapıldı", Toast.LENGTH_SHORT).show()
                    }catch (exception : Exception){ }

                } else {
                    try {
                        Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }catch (hata : Exception){ }

                }
            }
    }
}







