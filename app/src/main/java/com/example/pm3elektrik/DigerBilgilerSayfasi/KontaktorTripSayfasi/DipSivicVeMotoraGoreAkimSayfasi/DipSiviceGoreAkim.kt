package com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi.DipSivicVeMotoraGoreAkimSayfasi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.example.pm3elektrik.R

class DipSiviceGoreAkim : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_dip_sivice_gore_akim, container, false)

        return view
    }


}
