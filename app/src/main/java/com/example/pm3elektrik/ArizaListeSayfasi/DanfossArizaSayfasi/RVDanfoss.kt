package com.example.pm3elektrik.ArizaListeSayfasi.DanfossArizaSayfasi


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.rv_danfoss.view.*


class RVDanfoss (var arizaListe : ArrayList<DanfossArizaModel>, var mContext : Context) : RecyclerView.Adapter<RVDanfoss.MyData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_danfoss,parent,false)

        return MyData(view)
    }

    override fun getItemCount(): Int {
        return arizaListe.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {
        holder.setData(arizaListe.get(position),position)
    }




    inner class MyData(itemView : View) : RecyclerView.ViewHolder(itemView) {


        var tumLayout = itemView as ConstraintLayout
        var arizaKodu = tumLayout.tvAcs380ArizaKodu
        var tanim = tumLayout.tvArizaTanim

        fun setData(gelenAriza : DanfossArizaModel , position : Int){

            arizaKodu.setText(gelenAriza.arizaKodu)
            tanim.setText(gelenAriza.arizaTanim)

        }

    }

    fun gelenArizaKodunaGoreFiltrele(gelenArama: ArrayList<DanfossArizaModel>) {

        arizaListe = ArrayList<DanfossArizaModel>()
        arizaListe.addAll(gelenArama)
        notifyDataSetChanged()

    }


}
