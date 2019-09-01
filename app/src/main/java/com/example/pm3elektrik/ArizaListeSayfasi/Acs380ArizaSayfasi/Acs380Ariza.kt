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

        arizaListe.add(ACS380ArizaModel("FL1","Overcurrent"))
        arizaListe.add(ACS380ArizaModel("FL2","Motor Stall"))
        arizaListe.add(ACS380ArizaModel("FL3","Earth Fault"))

    }


}
