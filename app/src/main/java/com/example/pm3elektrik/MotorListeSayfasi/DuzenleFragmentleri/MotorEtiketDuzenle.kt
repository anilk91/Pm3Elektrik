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

//        val motorIsimDuzenle = view.findViewById<EditText>(R.id.etMotorIsim).text.toString()
//        val motorTagDuzenle = view.findViewById<EditText>(R.id.etMotorTag).text.toString()
//        val gucKwDuzenle = view.findViewById<EditText>(R.id.etGucKw).text.toString()
//        val gucHpDuzenle = view.findViewById<EditText>(R.id.etGucHP).text.toString()
//        val devirDuzenle = view.findViewById<EditText>(R.id.etDevir).text.toString()
//        val nomTripAkimDuzenle = view.findViewById<EditText>(R.id.etNomTripAkimi).text.toString()
//        val insaTipiDuzenle = view.findViewById<EditText>(R.id.etInsaTipi).text.toString()
//        val flansDuzenle = view.findViewById<EditText>(R.id.etFlans).text.toString()
//        val motorAdresDuzenle = view.findViewById<EditText>(R.id.etMotorAdres).text.toString()
//        val motorMCCYeriDuzenle = view.findViewById<EditText>(R.id.etMotorMCCYeri).text.toString()
//        val motorDegTarihDuzenle = view.findViewById<EditText>(R.id.etMotorDegTarihi).text.toString()


        val button_close = view.findViewById<ImageView>(R.id.imgMotorCLose)
        val button_ekle = view.findViewById<Button>(R.id.buttonMotorEkle)

        val bundle = arguments
        val gelenMotorTag = bundle!!.getString("motor_tag")

        fireBaseOkunanVeriler(gelenMotorTag!!)

        guckWveHpTextDinle()

        button_ekle.setOnClickListener {


//            motor_liste.motorGucKW = etGucKw.text.toString()
//            motor_liste.motorGucHP = etGucHP.text.toString()




//            ref.child(gelenMotorTag)
//                .addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onCancelled(p0: DatabaseError) {}
//
//                    override fun onDataChange(p0: DataSnapshot) {
//
//                        val okunan = p0.getValue(MotorModel::class.java)
//                        etMotorIsim.setText(okunan!!.motorIsim)
//                        etMotorTag.setText(okunan.motorTag)
//                        etGucKw.setText(okunan.motorGucKW)
//                        etGucHP.setText(okunan.motorGucHP)
//                        etDevir.setText(okunan.motorDevir)
//                        etNomTripAkimi.setText(okunan.motorNomTripAkimi)
//                        etInsaTipi.setText(okunan.motorInsaTipi)
//                        etFlans.setText(okunan.motorFlans)
//                        etMotorAdres.setText(okunan.motorAdres)
//                        etMotorMCCYeri.setText(okunan.motorMCCYeri)
//                        etMotorDegTarihi.setText(okunan.motorDegTarihi)
//
//                    }
//
//                })

            if (etMotorTag.text.toString().isNotEmpty() && etMotorMCCYeri.text.toString().isNotEmpty()) {

                FirebaseDBMotorEkle(
                    etMotorIsim.text.toString(), etMotorTag.text.toString(), etGucKw.text.toString(), etGucHP.text.toString(), etDevir.text.toString(), etNomTripAkimi.text.toString(),
                    etInsaTipi.text.toString(), etFlans.text.toString(), etMotorAdres.text.toString(), etMotorMCCYeri.text.toString(), etMotorDegTarihi.text.toString()
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





//        if (motorGucKW.isEmpty() && motorGucHP.isEmpty()) {
//
//            motor_liste.motorGucKW = motorGucKW
//            motor_liste.motorGucHP = motorGucHP
//
//        } else if (motorGucKW.isNotEmpty() ) {
//
//            val hp = motorGucHP.toDouble()
//            val kw_karsiligi = DecimalFormat("##.##").format(0.75*hp)
//            motor_liste.motorGucKW = "$kw_karsiligi"
//            motor_liste.motorGucHP = motorGucHP
//
//        } else if (motorGucHP.isNotEmpty()) {
//
//            val kw = motorGucKW.toDouble()
//            val hp_karsiligi = DecimalFormat("##.##").format(kw/0.75)
//            motor_liste.motorGucHP = "$hp_karsiligi"
//            motor_liste.motorGucKW = motorGucKW
//
//        } else {
//            motor_liste.motorGucKW = motorGucKW
//            motor_liste.motorGucHP = motorGucHP
//        }
        ref.child(motorTag)
            .setValue(motor_liste).addOnCompleteListener {

                if (it.isSuccessful) {
                    Toast.makeText(activity, "Kayıt Yapıldı", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun guckWveHpTextDinle(){

        etGucKw.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val kw : Double = etGucKw.text.toString().toDouble()
                //val hp_karsiligi :String = DecimalFormat("##.##").format(kw/0.75)
                val hp_karsiligi  = "${kw/0.75}"
                motor_liste.motorGucHP = hp_karsiligi
                motor_liste.motorGucKW = etGucKw.text.toString()

                Log.e("gucKW","$hp_karsiligi")

            }


        })

        etGucHP.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val hp = etGucHP.text.toString().toDouble()
                val kw_karsiligi = DecimalFormat("##.##").format(0.75*hp)
                motor_liste.motorGucKW = "$kw_karsiligi"
                motor_liste.motorGucHP = etGucHP.text.toString()

                Log.e("gucKW","$kw_karsiligi")

            }


        })
    }
}