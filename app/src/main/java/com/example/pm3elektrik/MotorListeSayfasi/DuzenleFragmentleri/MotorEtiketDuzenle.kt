package com.example.pm3elektrik.MotorListeSayfasi.DuzenleFragmentleri

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_motor_ekle.*
import java.text.DecimalFormat

class MotorEtiketDuzenle : Fragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Motor")
    val motor_liste = MotorModel()


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
                    val hp_karsiligi = String.format("%.1f" , kw/0.75)
                    motor_liste.motorGucHP = hp_karsiligi
                    motor_liste.motorGucKW = gucKw.text.toString()
                }

            }
        })

        gucHp.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {

               if(!p0.isNullOrEmpty()){
                   val hp = gucHp.text.toString().toDouble()
                   val kw_karsiligi =  String.format("%.1f" , 0.75*hp)
                   motor_liste.motorGucKW = kw_karsiligi
                   motor_liste.motorGucHP = gucHp.text.toString()
               }
            }
        })

        button_ekle.setOnClickListener {

            if (etMotorTag.text.toString().isNotEmpty() && etMotorMCCYeri.text.toString().isNotEmpty()) {


                FirebaseDBMotorEkle(
                    etMotorIsim.text.toString().toUpperCase(), etMotorTag.text.toString().toUpperCase(), etGucKw.text.toString().toUpperCase(), etGucHP.text.toString().toUpperCase(), etDevir.text.toString().toUpperCase(), etNomTripAkimi.text.toString().toUpperCase(),
                    etInsaTipi.text.toString().toUpperCase(), etFlans.text.toString().toUpperCase(), etMotorAdres.text.toString().toUpperCase(), etMotorMCCYeri.text.toString().toUpperCase(), etMotorDegTarihi.text.toString().toUpperCase()
                )

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
                    etGucKw.setText(okunan.motorGucKW)
                    etGucHP.setText(okunan.motorGucHP)
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

    fun FirebaseDBMotorEkle(motorIsim: String, motorTag: String, motorGucKW: String, motorGucHP: String, motorDevir: String, motorNomTripAkimi: String,
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
                    Toast.makeText(activity, "Kayıt Yapıldı", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}







