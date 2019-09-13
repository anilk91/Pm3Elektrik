package com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveRVAdapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel.DriveModel

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.rv_drive_unite_adapter.view.*


class DriveRVAdapter(var motorListe : ArrayList<DriveModel>, var mContext : Context) : RecyclerView.Adapter<DriveRVAdapter.MyData>() {


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


    inner class MyData(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tumLayout = itemView as ConstraintLayout
        val tag = tumLayout.tvDriveTag
        val guc = tumLayout.tvDriveGuc
        val seriNo = tumLayout.tvDriveSeriNo
        val degTarihi = tumLayout.tvDriveDegTarih
        val deleteButton = tumLayout.imgDriveDeleteButton as ImageView
        val bilgiButton = tumLayout.imgDriveBilgiButton as ImageView

        fun setData(gelenMotorListe: DriveModel, position: Int) {

            tag.setText(gelenMotorListe.driveTagNo)
            guc.setText(gelenMotorListe.driveGuc + " KVA")
            seriNo.setText(gelenMotorListe.driveSeriNo)
            degTarihi.setText(gelenMotorListe.driveDegTarihi)

            deleteButton.setOnClickListener {  }

            bilgiButton.setOnClickListener {  }

        }

    }


}
