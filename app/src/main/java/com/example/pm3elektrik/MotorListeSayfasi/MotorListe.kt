package com.example.pm3elektrik.MotorListeSayfasi

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.MotorEkle
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.SalterEkle
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.MotorListeSayfasi.RVAdapter.MotorRVAdapter
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import com.github.clans.fab.FloatingActionButton


class MotorListe : Fragment() {

    var motorListesi= ArrayList<MotorModel>()
    lateinit var mFAB_cekmece: FloatingActionButton
    lateinit var mFAB_motor: FloatingActionButton
    lateinit var motorListeLayout : ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_motor_liste, containerFragment, false)

        motorListesi.add(MotorModel("1HD18","2.1 Arka Hamur","30","1500"))
        motorListesi.add(MotorModel("2HD18","2.1 Ön Hamur","30","1500"))
        motorListesi.add(MotorModel("1DD65","7.3.1 Arka Hamur","30","1500"))
        motorListesi.add(MotorModel("1HD18","Ensturman az ilerisinde sağda pano arkasında","110","1500"))

        val motor_ara = view.findViewById<EditText>(R.id.etMotorArama)
        val rvList = view.findViewById<RecyclerView>(R.id.rvMotorListe)
        motorListeLayout = view.findViewById<ConstraintLayout>(R.id.motorListeLayout)
        val mContext = view.context as Context

        recyclerAdapter(mContext,rvList)


        //floating action bar buttonları eklendi
        mFAB_cekmece = view.findViewById(R.id.menu_cekmece)
        mFAB_motor = view.findViewById(R.id.menu_motor)

        mFAB_motor.setOnClickListener {

            changeFragment(MotorEkle())
        }
        mFAB_cekmece.setOnClickListener {

            changeFragment(SalterEkle())
        }

        return view
    }

    private fun recyclerAdapter(mContext: Context, rvList : RecyclerView) {
        val myAdapter = MotorRVAdapter(motorListesi,mContext)
        rvList.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mContext,RecyclerView.VERTICAL,false)
        rvList.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()

    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"fragment")
        fragmentTransaction.commit()

    }
}
