package com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pm3elektrik.R

class MotorEkle : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_motor_ekle, container, false)
    }


}
