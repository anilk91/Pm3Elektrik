package com.example.pm3elektrik.MotorListeSayfasi.RVAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorveSalterEtiketleri.MotorVeSalterEtiket
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.motor_rv_adapter.view.*

class MotorRVAdapter(var motorListe : ArrayList<MotorModel>, var mContext :Context):RecyclerView.Adapter<MotorRVAdapter.MyData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.motor_rv_adapter,parent,false)
        return MyData(view)
    }

    override fun getItemCount(): Int {

        return motorListe.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {
    holder.setData(motorListe.get(position),position)
    }

    inner class MyData(itemView : View):RecyclerView.ViewHolder(itemView) {

        var tumLayout = itemView as ConstraintLayout
        var motorTag = tumLayout.tvMotorEtiketTag
        var mCCYeri = tumLayout.tvMotorMCCYeri
        var motorGuc = tumLayout.tvMotorGuc
        var motorDevir = tumLayout.tvMotorDevir



        fun setData(motorListesi: MotorModel, position: Int) {

            motorTag.setText(motorListesi.motorTag)
            mCCYeri.setText(motorListesi.motorMCCYeri)
            motorGuc.setText("${motorListesi.motorGucKW}")
            motorDevir.setText(motorListesi.motorDevir)

            tumLayout.setOnClickListener {
                MotorListe().motorListedenEtiketBaslat(motorListe[position].motorTag)
            }


        }
    }

    fun gelenMotorTagiFiltrele(gelenTag : ArrayList<MotorModel>){

        motorListe = ArrayList<MotorModel>()
        motorListe.addAll(gelenTag)
        notifyDataSetChanged()


    }
}