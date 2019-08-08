package com.example.pm3elektrik.DigerBilgilerSayfasi.KumandaProjeSayfasi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pm3elektrik.R

class KumandaProjeleri : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kumanda_projeleri, container, false)



        return view
    }


}
