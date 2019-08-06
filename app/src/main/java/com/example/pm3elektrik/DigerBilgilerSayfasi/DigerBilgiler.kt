package com.example.pm3elektrik.DigerBilgilerSayfasi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayit

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.fragment_diger_bilgiler.view.*

class DigerBilgiler : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_diger_bilgiler, container, false)

        val ambarSayfasi= view.findViewById<CardView>(R.id.cardViewAmbarKayit)

        ambarSayfasi.setOnClickListener {

            changeFragment(AmbarKayit())

        }


        return view
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerDigerBilgiler,fragment,"fragment")
        fragmentTransaction.commit()

    }


}
