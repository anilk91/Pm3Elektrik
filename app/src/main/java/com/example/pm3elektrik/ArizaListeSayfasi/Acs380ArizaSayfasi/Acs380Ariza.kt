package com.example.pm3elektrik.ArizaListeSayfasi.Acs380ArizaSayfasi


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.fragment_acs380_ariza.view.*

class Acs380Ariza : Fragment() {

    val arizaListe = ArrayList<ACS380ArizaModel>()
    lateinit var myAdapter: RVAcs380

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_acs380_ariza, container, false)

        gelenArizalar()

        val arizaAra = view.findViewById<EditText>(R.id.etAcs380ArizaAra)
        arizaAra.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {

                if (p0 != null){

                    val gelenVeri = p0.toString().toUpperCase()
                    val arananlar = ArrayList<ACS380ArizaModel>()

                    for (gelen in arizaListe){

                        val bulunanArizaKodu = gelen.arizaKodu
                        if (bulunanArizaKodu.contains(gelenVeri)) {
                            arananlar.add(gelen)
                        }

                        if (myAdapter != null) {
                            myAdapter.gelenArizaKodunaGoreFiltrele(arananlar)
                        }
                    }
                }
            }
        })

        myAdapter = RVAcs380(arizaListe,view.context)
        view?.rvAcs380Liste?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(view.context,RecyclerView.VERTICAL,false)
        view?.rvAcs380Liste?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()



        return view
    }


    fun gelenArizalar(){

        arizaListe.add(ACS380ArizaModel("2001","Overcurrent","Output current limit controller is active. ","Check motor load","Check acceleration time (2202 and 2205).","Check motor and motor cable (including phasing).","Check ambient conditions. Load capacity decreases if installation site ambient temperature exceeds 40°C. See section Derating on page 291."))
        arizaListe.add(ACS380ArizaModel("2002","OverVoltage","DC overvoltage controller is active.","Check deceleration time (2203 and 2206).","Check input power line for static or transient overvoltage."))
        arizaListe.add(ACS380ArizaModel("2003","Undervoltage","DC undervoltage controller is active.","Check input power supply."))
        arizaListe.add(ACS380ArizaModel("2004","DIRLOCK","Change of direction is not allowed.","Check parameter 1003 DIRECTION settings."))
        arizaListe.add(ACS380ArizaModel("2005","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2006","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2007","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2008","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2009","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2010","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2011","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2012","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2013","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2018","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2019","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2021","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2022","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2023","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2024","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2025","Undervoltage","DC undervoltage controller is active.","Check input power supply"))
        arizaListe.add(ACS380ArizaModel("2026","Undervoltage","DC undervoltage controller is active.","Check input power supply"))

    }


}
