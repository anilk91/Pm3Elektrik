package com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi.DipSivicVeMotoraGoreAkimSayfasi.DipSiviceGoreAkim
import com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi.DipSivicVeMotoraGoreAkimSayfasi.MotorAkiminaGoreDipSivic
import com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi.KontaktorTripSiniflari.SizeBesveAlti
import com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi.KontaktorTripSiniflari.SizeBirveIki
import com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi.KontaktorTripSiniflari.SizeLowRange
import com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi.KontaktorTripSiniflari.SizeUcveDort

import com.example.pm3elektrik.R

class KontaktorTrip : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kontaktor_trip, container, false)

        val sizeLowRange = view.findViewById<CardView>(R.id.cardViewLowRange)
        val sizeBirveIki = view.findViewById<CardView>(R.id.cardViewSize1ve2)
        val sizeUcveDort = view.findViewById<CardView>(R.id.cardViewSize3ve4)
        val sizeBesveAlti = view.findViewById<CardView>(R.id.cardViewSize5ve6)
        val motorAkiminaGoreDip = view.findViewById<CardView>(R.id.cardMotorAkGoreDip)
        val dipSiviceGoreMotorAkimi = view.findViewById<CardView>(R.id.cardDipSivicMotorAkBul)

        motorAkiminaGoreDip.setOnClickListener {
            MotorAkiminaGoreDipSivic().show(fragmentManager!!,"dialog_fr_motor_akima_gore")
        }

        dipSiviceGoreMotorAkimi.setOnClickListener {
            DipSiviceGoreAkim().show(fragmentManager!!,"dialog_fr_dip_sivice_gore")
        }

        sizeLowRange.setOnClickListener { changeFragment(SizeLowRange()) }
        sizeBirveIki.setOnClickListener { changeFragment(SizeBirveIki()) }
        sizeUcveDort.setOnClickListener { changeFragment(SizeUcveDort()) }
        sizeBesveAlti.setOnClickListener { changeFragment(SizeBesveAlti()) }

        return view
    }


    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerKontaktorTripSayfasi,fragment,"kontaktor_trip_sayfasi")
        fragmentTransaction.addToBackStack("kontaktor_trip_ana_sayfa")
        fragmentTransaction.commit()

    }


}
