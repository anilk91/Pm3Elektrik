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
        var arizaKodu = tumLayout.tvAcs380ArizaKodu
        var tanim = tumLayout.tvAcs380Tanim
        var tanimDetay = tumLayout.tvAcs380TanimDetay
        var aciklama1 = tumLayout.tvAcs380Aciklama1
        var aciklama2 = tumLayout.tvAcs380Aciklama2
        var aciklama3 = tumLayout.tvAcs380Aciklama3
        var aciklama4 = tumLayout.tvAcs380Aciklama4
        var aciklama5 = tumLayout.tvAcs380Aciklama5

        fun setData(gelenAriza : ACS380ArizaModel, position: Int) {

            arizaKodu.setText(gelenAriza.arizaKodu)
            tanim.setText(gelenAriza.tanim)
            tanimDetay.setText(gelenAriza.tanimDetay)
            aciklama1.setText(gelenAriza.aciklama1)
            aciklama2.setText(gelenAriza.aciklama2)
            aciklama3.setText(gelenAriza.aciklama3)
            aciklama4.setText(gelenAriza.aciklama4)
            aciklama5.setText(gelenAriza.aciklama5)
        }
    }

    fun gelenArizaKodunaGoreFiltrele(gelenArama : ArrayList<ACS380ArizaModel>) {

        arizaListe = ArrayList<ACS380ArizaModel>()
        arizaListe.addAll(gelenArama)
        notifyDataSetChanged()
    }
}
