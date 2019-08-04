package com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pm3elektrik.MotorListeSayfasi.SurucuEkle

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.fragment_kontaktor_ekle.*


class KontaktorEkle : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kontaktor_ekle, container, false)

        val cat = etKontaktorCat.text.toString()
        val degisimTarihi = etKontaktorDegisimTarihi.text.toString()
        val DIPSivic = etKontaktorDIPSivic.text.toString()

        val surucuEkle = container?.context as SurucuEkle
        surucuEkle.KontaktorEkle(cat,DIPSivic,degisimTarihi)

        return view
    }


}
