package com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.RVTrafoAdapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoModel.TrafoModel

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.rv_trafo_adapter.view.*

class RVTrafo(var trafoListesi : ArrayList<TrafoModel>, var mContext : Context) : RecyclerView.Adapter<RVTrafo.MyData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_trafo_adapter,parent,false)

        return MyData(view)
    }

    override fun getItemCount(): Int {

        return trafoListesi.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {

        holder.setData(trafoListesi[position],position)
    }


    class MyData(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tumLayout = itemView as ConstraintLayout
        val isim = tumLayout.tvTrafoIsim


        fun setData(gelenTrafoListesi: TrafoModel, position: Int) {

            isim.setText(gelenTrafoListesi.trafoIsim)
        }

    }


}
