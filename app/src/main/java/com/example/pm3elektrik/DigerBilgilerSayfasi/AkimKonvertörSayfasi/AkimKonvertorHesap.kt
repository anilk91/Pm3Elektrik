package com.example.pm3elektrik.DigerBilgilerSayfasi.AkimKonvertörSayfasi


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.fragment_akim_konvertor_hesap.*
import java.math.BigDecimal
import java.math.RoundingMode

class AkimKonvertorHesap : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_akim_konvertor_hesap, container, false)

        val ekle = view.findViewById<Button>(R.id.buttonAkimBilgisiHesapla)
        val close = view.findViewById<ImageView>(R.id.imgAkimBilgisiClose)
        val sonucMA = view.findViewById<TextView>(R.id.tvSonucMA)
        val sonucYuzde = view.findViewById<TextView>(R.id.tvSonucYuzde)



        close.setOnClickListener {
            dismiss()
        }

        ekle.setOnClickListener {

            val nominalAkim = etNominalAkim.text.toString()
            val anlikmA = etAnlikCikisAkimi.text.toString()

            if(nominalAkim.isNotEmpty() && anlikmA.isNotEmpty()){

                val mA = (anlikmA.toDouble() * 20)/nominalAkim.toDouble()
                val yuzde = (anlikmA.toDouble() * 100)/nominalAkim.toDouble()


                sonucMA.setText("${BigDecimal(mA).setScale(1, RoundingMode.HALF_EVEN)} mA")
                sonucYuzde.setText("% ${BigDecimal(yuzde).setScale(1,RoundingMode.HALF_EVEN)}")


            }else{
                Toast.makeText(activity,"Boş Alanları Doldurunuz",Toast.LENGTH_LONG).show()
            }
        }


        return view
    }


}
