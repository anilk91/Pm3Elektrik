package com.example.pm3elektrik.DigerBilgilerSayfasi.DriveUniteleriSayfasi.DriveRVAdapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

import com.example.pm3elektrik.R


class DriveRVAdapter(var motorListe : ArrayList<String>,var mContext : Context) : RecyclerView.Adapter<DriveRVAdapter.MyData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_drive_unite_adapter,parent,false)

        return MyData(view)
    }

    override fun getItemCount(): Int {

        return motorListe.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {

        holder.setData(motorListe.get(position),position)
    }


    inner class MyData(view: View) : RecyclerView.ViewHolder(view){


        fun setData(gelenMotorListe: String, position: Int) {

        }

    }


}
