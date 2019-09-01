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
import com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi.KontaktorTripSiniflari.SizeLowRange

import com.example.pm3elektrik.R

class KontaktorTrip : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kontaktor_trip, container, false)

        val sizeLowRange = view.findViewById<CardView>(R.id.cardViewLowRange)
        val sizeBir = view.findViewById<CardView>(R.id.cardViewSize1)
        val sizeIki = view.findViewById<CardView>(R.id.cardViewSize2)
        val sizeUc = view.findViewById<CardView>(R.id.cardViewSize3)
        val sizeDort = view.findViewById<CardView>(R.id.cardViewSize4)
        val sizeBes = view.findViewById<CardView>(R.id.cardViewSize5)
        val motorAkiminaGoreDip = view.findViewById<CardView>(R.id.cardMotorAkGoreDip)
        val dipSiviceGoreMotorAkimi = view.findViewById<CardView>(R.id.cardDipSivicMotorAkBul)

        motorAkiminaGoreDip.setOnClickListener {
            MotorAkiminaGoreDipSivic().show(fragmentManager,"dialog_fr_motor_akima_gore")
        }

        dipSiviceGoreMotorAkimi.setOnClickListener {
            DipSiviceGoreAkim().show(fragmentManager,"dialog_fr_dip_sivice_gore")
        }

        sizeLowRange.setOnClickListener {
            changeFragment(SizeLowRange())
        }

        sizeBir.setOnClickListener {  }
        sizeIki.setOnClickListener {  }
        sizeUc.setOnClickListener {  }
        sizeDort.setOnClickListener {  }
        sizeBes.setOnClickListener {  }





        return view
    }


    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerKontaktorTripSayfasi,fragment,"kontaktor_trip_sayfasi")
        fragmentTransaction.commit()

    }


}
