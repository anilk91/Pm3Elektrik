package com.example.pm3elektrik.DigerBilgilerSayfasi.KumandaProjeSayfasi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.DigerBilgilerSayfasi.KumandaProjeSayfasi.FrekansKonvertoruKumandaProjeSayfasi.FrekansKonvertoruKumanda
import com.example.pm3elektrik.DigerBilgilerSayfasi.KumandaProjeSayfasi.ItSoftStarterKumandaProjeSayfasi.ItSoftStarterKumanda
import com.example.pm3elektrik.DigerBilgilerSayfasi.KumandaProjeSayfasi.KuyuPanoKumandaProjeSayfasi.KuyuPanoKumanda
import com.example.pm3elektrik.DigerBilgilerSayfasi.KumandaProjeSayfasi.Tip206KumandaProjeSayfasi.Tip206Kumanda
import com.example.pm3elektrik.DigerBilgilerSayfasi.KumandaProjeSayfasi.Tip216KumandaProjeSayfasi.Tip216Kumanda
import com.example.pm3elektrik.DigerBilgilerSayfasi.KumandaProjeSayfasi.ValmetHidrolikKumandaProjeSayfasi.ValmetHidrolikKumanda

import com.example.pm3elektrik.R

class KumandaProjeleri : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kumanda_projeleri, container, false)

        val tip206 = view.findViewById<CardView>(R.id.cardView206Tipi)
        val tip216 = view.findViewById<CardView>(R.id.cardView216Tipi)
        val itSoftStarter = view.findViewById<CardView>(R.id.cardViewITSoftStarter)
        val frekansKonvertor = view.findViewById<CardView>(R.id.cardViewFrekansKonvertor)
        val valmetHidrolik = view.findViewById<CardView>(R.id.cardViewValmetHidrolik)
        val kuyuPano = view.findViewById<CardView>(R.id.cardViewKuyuPanolari)

        tip206.setOnClickListener { changeFragment(Tip206Kumanda()) }
        tip216.setOnClickListener { changeFragment(Tip216Kumanda()) }
        itSoftStarter.setOnClickListener { changeFragment(ItSoftStarterKumanda()) }
        frekansKonvertor.setOnClickListener { changeFragment(FrekansKonvertoruKumanda()) }
        valmetHidrolik.setOnClickListener { changeFragment(ValmetHidrolikKumanda()) }
        kuyuPano.setOnClickListener { changeFragment(KuyuPanoKumanda()) }

        return view
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.containerKumandaProje,fragment,"fragment_motor_liste")
        fragmentTransaction?.addToBackStack("kumanda_proje_ana_sayfa")
        fragmentTransaction?.commit()
    }


}
