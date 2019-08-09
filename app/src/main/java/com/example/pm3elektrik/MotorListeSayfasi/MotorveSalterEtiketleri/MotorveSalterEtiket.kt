package com.example.pm3elektrik.MotorListeSayfasi.MotorveSalterEtiketleri


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.MotorEkle
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.SalterEkle
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel

import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MotorveSalterEtiket : Fragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_motorve_salter_etiket, container, false)
        val motorSalterEtiketClose = view.findViewById<ImageView>(R.id.imgMotorSalterClose)
        val motorEdit = view.findViewById<EditText>(R.id.imgEditMotorBilgi)
        val salterSurucuEdit = view.findViewById<EditText>(R.id.imgEditSalterSurucu)

        motorSalterEtiketClose.setOnClickListener {

            changeFragment(MotorListe())
        }
        motorEdit.setOnClickListener {

            changeFragment(MotorEkle())
        }
        salterSurucuEdit.setOnClickListener {
            changeFragment(SalterEkle())
        }

        firebaseDBOku()



        return view
    }

    private fun firebaseDBOku() {

        val motorIsim = view?.findViewById<TextView>(R.id.tvMotorEtiketPompaIsim)
        val motorTag = view?.findViewById<TextView>(R.id.tvMotorEtiketTag)
        val motorGucKW = view?.findViewById<TextView>(R.id.tvMotorEtiketGucKw)
        val motorGucHP = view?.findViewById<TextView>(R.id.tvMotorEtiketGucHp)
        val motorDevir = view?.findViewById<TextView>(R.id.tvMotorEtiketDevir)
        val motorNomTrip = view?.findViewById<TextView>(R.id.tvMotorEtiketNomTripAkim)
        val motorInsaTipi = view?.findViewById<TextView>(R.id.tvMotorEtiketInsaTipi)
        val motorFlans = view?.findViewById<TextView>(R.id.tvMotorEtiketFlans)
        val motorAdres = view?.findViewById<TextView>(R.id.tvMotorEtiketDevir)
        val motorMccYeri = view?.findViewById<TextView>(R.id.tvMotorEtiketMccYeri)
        val motorDegTarihi = view?.findViewById<TextView>(R.id.tvMotorEtiketDegTarih)


        ref.child("Motor")
            .child("3PD14")
            .addListenerForSingleValueEvent( object  : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {

                    val getir = p0.getValue(MotorModel::class.java)
                    motorIsim?.setText("${getir?.motorIsim}")
                    motorTag?.setText("${getir?.motorTag}")
                    motorGucKW?.setText("${getir?.motorGucKW}")
                    motorGucHP?.setText("${getir?.motorGucHP}")
                    motorDevir?.setText("${getir?.motorDevir}")
                    motorNomTrip?.setText("${getir?.motorNomTripAkimi}")
                    motorInsaTipi?.setText("${getir?.motorInsaTipi}")
                    motorFlans?.setText("${getir?.motorFlans}")
                    motorAdres?.setText("${getir?.motorAdres}")
                    motorMccYeri?.setText("${getir?.motorMCCYeri}")
                    motorDegTarihi?.setText("${getir?.motorDegTarihi}")


                }


            })



    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorSalterEtiket,fragment,"fragment")
        fragmentTransaction.commit()

    }


}
