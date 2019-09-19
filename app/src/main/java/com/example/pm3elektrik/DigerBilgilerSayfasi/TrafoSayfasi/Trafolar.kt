package com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.RVTrafoAdapter.RVTrafo
import com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoModel.TrafoModel

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.fragment_trafolar.view.*

class Trafolar : Fragment() {

    lateinit var myAdapter : RVTrafo
    val trafoListesi = ArrayList<TrafoModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_trafolar, container, false)

        trafoListesi.add(TrafoModel("REGEL (ANA) TRAFO"))
        trafoListesi.add(TrafoModel("TRAFO 1"))
        trafoListesi.add(TrafoModel("TRAFO 2"))
        trafoListesi.add(TrafoModel("TRAFO 3"))
        trafoListesi.add(TrafoModel("TRAFO 4"))
        trafoListesi.add(TrafoModel("TRAFO 5"))
        trafoListesi.add(TrafoModel("TRAFO 6"))
        trafoListesi.add(TrafoModel("TRAFO 7"))
        trafoListesi.add(TrafoModel("TRAFO 8"))
        trafoListesi.add(TrafoModel("TRAFO 9"))
        trafoListesi.add(TrafoModel("TRAFO 10"))
        trafoListesi.add(TrafoModel("TRAFO 11"))
        trafoListesi.add(TrafoModel("TRAFO 12"))
        trafoListesi.add(TrafoModel("TRAFO 13"))

        myAdapter = RVTrafo(trafoListesi,view.context , activity)
        view.rvTrafoListesi.adapter = myAdapter
        val mLayoutManager = GridLayoutManager(view.context,3,RecyclerView.VERTICAL,false)
        view.rvTrafoListesi.layoutManager = mLayoutManager


        return view
    }


}
