package com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoEtiketDuzenle


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.example.pm3elektrik.R

class TrafoGosterDuzenle : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_trafo_goster_duzenle, container, false)

        val edit = view.findViewById<ImageView>(R.id.imgTrafoEtiketDuzenle)

        edit.setOnClickListener {  }


        return view
    }
}
