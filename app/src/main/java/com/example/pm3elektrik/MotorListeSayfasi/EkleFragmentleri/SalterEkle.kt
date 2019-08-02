package com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe

import com.example.pm3elektrik.R

class SalterEkle : Fragment() {

    val secimListesi = arrayOf("Sürücü Seçiniz", "Kontaktör", "Frekans Konvertörü")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_salter_ekle, container, false)
        val surucuLayout = view.findViewById<FrameLayout>(R.id.surucuLayout)
        val surucuSpinner = view.findViewById<Spinner>(R.id.spinnerSurucuSecim)
        surucuSpinner!!.adapter = ArrayAdapter(container!!.context, android.R.layout.simple_spinner_dropdown_item, secimListesi)

        surucuSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position == 1){

                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.surucuLayout,KontaktorEkle(),"kontaktorEkle")
                    fragmentTransaction?.commit()

                }else if (position == 2){

                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.surucuLayout,FrekansKonvertorEkle(),"kontaktorEkle")
                    fragmentTransaction?.commit()
                }
            }


        }



        val button_close = view.findViewById<ImageView>(R.id.imgSalterClose)
        button_close.setOnClickListener {

            changeFragment(MotorListe())
        }


        return view
    }

    private fun changeFragment(fragment : Fragment){
        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"fragment")
        fragmentTransaction.commit()

    }


}
