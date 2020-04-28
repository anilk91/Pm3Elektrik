package com.example.pm3elektrik.ArizaListeSayfasi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.ArizaListeSayfasi.Acs140ArizaSayfasi.Acs140Ariza
import com.example.pm3elektrik.ArizaListeSayfasi.Acs380ArizaSayfasi.Acs380Ariza
import com.example.pm3elektrik.ArizaListeSayfasi.DanfossArizaSayfasi.DanfossAriza
import com.example.pm3elektrik.ArizaListeSayfasi.ItSoftStarterSayfasi.ItSoftStarterAriza

import com.example.pm3elektrik.R


class ArizaListe : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ariza_liste, container, false)

        val itSoftStarter = view.findViewById<CardView>(R.id.cardViewItSoftStarter)
        val acs140 = view.findViewById<CardView>(R.id.cardViewACS140)
//        val acs380 = view.findViewById<CardView>(R.id.cardViewACS380)
        val danfoss = view.findViewById<CardView>(R.id.cardViewDanfoss)
        itSoftStarter.setOnClickListener {
            changeFragment(ItSoftStarterAriza())
        }
        acs140.setOnClickListener {
            changeFragment(Acs140Ariza())
        }
//        acs380.setOnClickListener {
//            changeFragment(Acs380Ariza())
//        }
        danfoss.setOnClickListener {
            changeFragment(DanfossAriza())
        }

        return view
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerArizaListe,fragment,"ariza_liste_fr").addToBackStack("ariza_liste_sayfasi")
        fragmentTransaction.commit()



    }




}
