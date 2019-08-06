package com.example.pm3elektrik.TelefonListeSayfasi.TelefonRVAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.R
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonModel.TelefonListeModel
import kotlinx.android.synthetic.main.telefon_rv_adapter.view.*

class TelefonRV(var telefonListe : ArrayList<TelefonListeModel>) : RecyclerView.Adapter<TelefonRV.MyData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.telefon_rv_adapter,parent,false)

        return MyData(view)
    }

    override fun getItemCount(): Int {

       return telefonListe.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {

        holder.setData(telefonListe.get(position),position)
    }


    inner class MyData(itemView : View) :RecyclerView.ViewHolder(itemView){

        val tumLayout = itemView as ConstraintLayout
        val telefonIsim = tumLayout.tvTelefonIsim
        val telefonNo = tumLayout.tvTelefonNo

        fun setData(telefon : TelefonListeModel , position: Int){

            telefonIsim.setText(telefon.telefonIsim)
            telefonNo.setText(telefon.telefonNo)


        }

    }
}