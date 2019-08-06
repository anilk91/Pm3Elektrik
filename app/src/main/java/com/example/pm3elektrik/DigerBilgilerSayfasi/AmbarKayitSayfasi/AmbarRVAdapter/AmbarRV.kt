package com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarRVAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitModel.AmbarKayitModeli
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.ambar_rv_adapter.view.*

class AmbarRV(var ambarListe: ArrayList<AmbarKayitModeli>, var mContext: Context) :
    RecyclerView.Adapter<AmbarRV.MyData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.ambar_rv_adapter, parent, false)
        return MyData(view)
    }

    override fun getItemCount(): Int {

        return ambarListe.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {
        holder.setData(ambarListe.get(position), position)
    }

    inner class MyData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tumLayout = itemView as ConstraintLayout
        val stokNo = tumLayout.tvAmbarStokNo
        val rafNo = tumLayout.tvAmbarRafNo
        val tanim = tumLayout.tvAmbarTanim

        fun setData(ambarListe: AmbarKayitModeli, position: Int) {

            stokNo.setText(ambarListe.ambarStokNo)
            rafNo.setText(ambarListe.ambarRafNo)
            tanim.setText(ambarListe.ambarTanim)
        }
    }
}
