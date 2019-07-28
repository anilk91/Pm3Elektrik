package com.example.pm3elektrik.MotorListeSayfasi

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView

import com.example.pm3elektrik.R

class MotorListe : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_motor_liste, container, false)

        val ekle_butonu = view.findViewById<Button>(R.id.ekle_butonu)
        val search_bar = view.findViewById<SearchView>(R.id.searchView)

        return view
    }

}
