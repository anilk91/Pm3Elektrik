package com.example.pm3elektrik.DigerBilgilerSayfasi.DriveUniteleriSayfasi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.DigerBilgilerSayfasi.DriveUniteleriSayfasi.DriveRVAdapters.DriveRVAdapter
import com.example.pm3elektrik.DigerBilgilerSayfasi.DriveUniteleriSayfasi.DriveUniteEkle.DriveUniteEkle
import com.example.pm3elektrik.DigerBilgilerSayfasi.DriveUniteleriSayfasi.DriveUniteModel.DriveModel

import com.example.pm3elektrik.R
import com.github.clans.fab.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_drive_unite.view.*

class DriveUnite : Fragment() {

    lateinit var uniteEkle : FloatingActionButton
    val driveMotorListe = ArrayList<DriveModel>()
    lateinit var myAdapter : DriveRVAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drive_unite, container, false)

        driveEkle()
        myAdapter = DriveRVAdapter(driveMotorListe,view.context)
        view.rvDriveUniteList.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(view.context,RecyclerView.VERTICAL,false)
        view.rvDriveUniteList.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()

        uniteEkle = view.findViewById(R.id.menuEkle)
        uniteEkle.setOnClickListener { changeFragment(DriveUniteEkle()) }




        return view
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.containerDriveUnite,fragment,"fragment_drive_unite")
        fragmentTransaction?.commit()
    }

    fun driveEkle(){

        driveMotorListe.add(DriveModel("6BG03","490","1993400177","12.12.2016"))
        driveMotorListe.add(DriveModel("6CG06","490","1993400115","12.12.2016"))
        driveMotorListe.add(DriveModel("6CG05","180","1993401436","12.12.2016"))
    }

}
