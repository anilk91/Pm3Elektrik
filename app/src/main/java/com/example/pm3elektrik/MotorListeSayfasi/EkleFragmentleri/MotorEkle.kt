package com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.FirebaseDatabase.FireDatabase
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe

import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_motor_ekle.*

class MotorEkle : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_motor_ekle, container, false)
        val button_close = view.findViewById<ImageView>(R.id.imgMotorCLose)
        val button_ekle = view.findViewById<Button>(R.id.buttonMotorEkle)
        val motor_tag = view.findViewWithTag<EditText>(R.id.etMotorTag)
        val guc_kw = view.findViewWithTag<EditText>(R.id.etGucKw)
        val guc_hp = view.findViewWithTag<EditText>(R.id.etGucHP)
        val devir = view.findViewWithTag<EditText>(R.id.etDevir)
        val nom_trip_akimi = view.findViewWithTag<EditText>(R.id.etNomTripAkimi)
        val insa_tipi = view.findViewWithTag<EditText>(R.id.etInsaTipi)
        val flans = view.findViewWithTag<EditText>(R.id.etFlans)
        val adres = view.findViewWithTag<EditText>(R.id.etMotorAdres)
        val mcc_yeri = view.findViewWithTag<EditText>(R.id.etMotorMCCYeri)
        val degisim_tarihi = view.findViewWithTag<EditText>(R.id.etMotorDegTarihi)


        button_ekle.setOnClickListener {

            val ref = FirebaseDatabase.getInstance().reference
            if (motor_tag.text.isNotEmpty() && mcc_yeri.text.isNotEmpty()){

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


}
