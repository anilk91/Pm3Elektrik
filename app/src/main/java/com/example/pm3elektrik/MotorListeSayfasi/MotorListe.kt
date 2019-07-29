package com.example.pm3elektrik.MotorListeSayfasi

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorListesiModeli
import com.example.pm3elektrik.MotorListeSayfasi.RVAdapter.MotorRVAdapter
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import kotlinx.android.synthetic.main.fragment_motor_liste.view.*

class MotorListe : Fragment() {

    var motorListesi= ArrayList<MotorListesiModeli>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_motor_liste, containerFragment, false)

        motorListesi.add(MotorListesiModeli("1HD18","2.1 Arka Hamur","30","1500"))
        motorListesi.add(MotorListesiModeli("2HD18","2.1 Ön Hamur","30","1500"))
        motorListesi.add(MotorListesiModeli("1DD65","7.3.1 Arka Hamur","30","1500"))
        motorListesi.add(MotorListesiModeli("1HD18","Ensturman az ilerisinde sağda pano arkasında","110","1500"))

        val ekle_butonu = view.findViewById<ImageView>(R.id.imgAddMotor)
        val motor_ara = view.findViewById<EditText>(R.id.etMotorArama)
        val rvList = view.findViewById<RecyclerView>(R.id.rvMotorListe)

        val mContext = view.context as Context


        val myAdapter = MotorRVAdapter(motorListesi,mContext)
        rvList.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mContext,RecyclerView.VERTICAL,false)
        rvList.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()



        return view
    }

}
