package com.example.pm3elektrik.ArizaListeSayfasi.Acs140ArizaSayfasi


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.rv_acs_140.view.*

class RVAcs140(var arizaListe : ArrayList<ACS140ArizaModel>, var mContext : Context) : RecyclerView.Adapter<RVAcs140.MyData>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_acs_140,parent,false)

        return MyData(view)
    }

    override fun getItemCount(): Int {

        return arizaListe.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {


        holder.setData(arizaListe.get(position),position)

    }

    inner class MyData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tumLayout = itemView as ConstraintLayout
        var arizaKodu = tumLayout.tvAcs380ArizaKodu
        var tanim = tumLayout.tvArizaTanim
        var madde1 = tumLayout.tvMadde1
        var madde2 = tumLayout.tvMadde2
        var madde3 = tumLayout.tvMadde3


        fun setData(gelenAriza: ACS140ArizaModel, position: Int) {

            arizaKodu.setText(gelenAriza.arizaKodu)
            tanim.setText(gelenAriza.tanim)
            madde1.setText(gelenAriza.madde1)
            madde2.setText(gelenAriza.madde2)
            madde3.setText(gelenAriza.madde3)

            if (gelenAriza.madde3.isNullOrBlank()){
                madde3.visibility = View.GONE
                madde2.visibility = View.VISIBLE
                madde1.visibility = View.VISIBLE
            }else{
                madde3.visibility = View.VISIBLE
            }
            if (gelenAriza.madde2.isNullOrBlank()){
                madde2.visibility = View.GONE
                madde1.visibility = View.VISIBLE
            }else{
                madde2.visibility = View.VISIBLE
            }
            if (gelenAriza.madde1.isNullOrBlank()){
                madde1.visibility = View.GONE
            }else {
                madde1.visibility = View.VISIBLE
            }

        }

    }

    fun gelenArizaKodunaGoreFiltrele(gelenArama: ArrayList<ACS140ArizaModel>) {

        arizaListe = ArrayList<ACS140ArizaModel>()
        arizaListe.addAll(gelenArama)
        notifyDataSetChanged()

    }


}
