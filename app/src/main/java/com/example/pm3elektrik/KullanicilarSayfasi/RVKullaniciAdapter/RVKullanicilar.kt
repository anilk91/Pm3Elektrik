package com.example.pm3elektrik.KullanicilarSayfasi.RVKullaniciAdapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.KullanicilarSayfasi.KullaniciModel.KullanicilarModel

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.rv_kullanicilar.view.*

class RVKullanicilar(var kullaniciListe : ArrayList<KullanicilarModel>, mContext : Context) : RecyclerView.Adapter<RVKullanicilar.MyData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_kullanicilar,parent,false)

        return MyData(view)

    }

    override fun getItemCount(): Int {
       return kullaniciListe.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {
        holder.setData(kullaniciListe[position],position)
    }


    inner class MyData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tumLayout = itemView as ConstraintLayout
        val isim = tumLayout.tvRVKullaniciIsim
        val sicilNo = tumLayout.tvRVKullaniciSicilNo


        fun setData(gelenKullanicilar: KullanicilarModel, position: Int) {

            isim.setText(gelenKullanicilar.isim)
            sicilNo.setText("${gelenKullanicilar.sicilNo}")

        }

    }


}
