package com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
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
