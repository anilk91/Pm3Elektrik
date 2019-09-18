package com.example.pm3elektrik.DigerBilgilerSayfasi.KuyuPanoSayfasi.KuyuPanoRVAdapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.DigerBilgilerSayfasi.KuyuPanoSayfasi.KuyuPanoModel.KuyuPanoModel

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.rv_kuyu_pano.view.*

class RVKuyuPano(var kuyuListe : ArrayList<KuyuPanoModel>, var mContext : Context) : RecyclerView.Adapter<RVKuyuPano.MyData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_kuyu_pano,parent,false)

        return MyData(view)
    }

    override fun getItemCount(): Int {

        return kuyuListe.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {

        holder.setData(kuyuListe[position],position)

    }


    inner class MyData(itemView : View) : RecyclerView.ViewHolder(itemView){

        val tumLayout = itemView as ConstraintLayout
        val isim = tumLayout.tvKuyuIsim
        val adres = tumLayout.tvKuyuAdres
        val surucuTipi = tumLayout.tvKuyuSurucuTipi
        val panoDegTarih = tumLayout.tvKuyuPanoDegTarihi
        val kuyuBesleme = tumLayout.tvKuyuPanoAnaBesleme

        fun setData(gelenKuyuListe : KuyuPanoModel, position: Int){

            isim.setText(gelenKuyuListe.kuyuIsim)
            adres.setText(gelenKuyuListe.kuyuAdres)
            surucuTipi.setText(gelenKuyuListe.kuyuSurucu)
            panoDegTarih.setText(gelenKuyuListe.kuyuDegTarihi)
            kuyuBesleme.setText(gelenKuyuListe.kuyuAnaBesleme)

        }

    }


}
