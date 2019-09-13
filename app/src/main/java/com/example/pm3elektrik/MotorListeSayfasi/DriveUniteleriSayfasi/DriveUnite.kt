package com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveRVAdapters.DriveRVAdapter
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteEkle.DriveUniteEkle
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel.DriveModel

import com.example.pm3elektrik.R
import com.github.clans.fab.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_drive_unite.view.*

class DriveUnite : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drive_unite, container, false)



        return view
    }


}
