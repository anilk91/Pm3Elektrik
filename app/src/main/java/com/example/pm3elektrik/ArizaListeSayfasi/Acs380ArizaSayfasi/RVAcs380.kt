package com.example.pm3elektrik.ArizaListeSayfasi.Acs380ArizaSayfasi


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.rv_acs380.view.*

class RVAcs380 (var arizaListe : ArrayList<ACS380ArizaModel> , var mContext : Context) : RecyclerView.Adapter<RVAcs380.MyData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_acs380,parent,false)

        return MyData(view)
    }

    override fun getItemCount(): Int {

        return arizaListe.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {

        holder.setData(arizaListe.get(position),position)
    }


    inner class MyData(itemView: View) : RecyclerView.ViewHolder(itemView){


        var tumLayout = itemView as ConstraintLayout
        var arizaKodu = tumLayout.tvArizaKodu
        var tanim = tumLayout.tvTanim

        fun setData(gelenAriza : ACS380ArizaModel, position: Int) {

            arizaKodu.setText(gelenAriza.arizaKodu)
            tanim.setText(gelenAriza.tanim)
        }
    }

    fun gelenArizaKodunaGoreFiltrele(gelenArama : ArrayList<ACS380ArizaModel>) {

        arizaListe = ArrayList<ACS380ArizaModel>()
        arizaListe.addAll(gelenArama)
        notifyDataSetChanged()
    }
}
