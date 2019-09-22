package com.example.pm3elektrik.ArizaListeSayfasi.ItSoftStarterSayfasi


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.rv_it_soft_starter.view.*

class RVItSoftStarter (var arizaListe : ArrayList<ItSoftStarterArizaModel>,mContext : Context) : RecyclerView.Adapter<RVItSoftStarter.MyData>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_it_soft_starter,parent,false)

        return MyData(view)
    }

    override fun getItemCount(): Int {

        return arizaListe.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {

        holder.setData(arizaListe.get(position),position)
    }


    inner class MyData(itemView : View) : RecyclerView.ViewHolder(itemView){

        var tumLayout = itemView as ConstraintLayout
        var arizaKodu = tumLayout.tvAcs380ArizaKodu
        var aciklama = tumLayout.tvArizaTanim
        var ekstraAciklama = tumLayout.tvEkstraArizaTanim


        fun setData(gelenAriza: ItSoftStarterArizaModel , position: Int) {

            arizaKodu.setText(gelenAriza.arizaKodu)
            aciklama.setText(gelenAriza.aciklama)
            ekstraAciklama.setText(gelenAriza.ekstraAciklama)

        }


    }

    fun gelenArizaKodunaGoreFiltrele(gelenArama: ArrayList<ItSoftStarterArizaModel>) {

        arizaListe = ArrayList<ItSoftStarterArizaModel>()
        arizaListe.addAll(gelenArama)
        notifyDataSetChanged()

    }


}
