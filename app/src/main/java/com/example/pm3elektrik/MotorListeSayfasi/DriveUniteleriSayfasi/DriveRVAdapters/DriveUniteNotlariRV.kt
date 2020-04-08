package com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveRVAdapters


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel.UniteNotuModel

import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.rv_drive_unite_notlari.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DriveUniteNotlariRV(var uniteNotListesi: ArrayList<UniteNotuModel>, var mContext: Context, var motorTag: String) : RecyclerView.Adapter<DriveUniteNotlariRV.MyData>() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("DriveUniteNot")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.rv_drive_unite_notlari,parent,false)

        return MyData(view)

    }

    override fun getItemCount(): Int {

        return uniteNotListesi.size
    }

    override fun onBindViewHolder(holder: MyData, position: Int) {

        holder.setData(uniteNotListesi[position],position)
    }


    inner class MyData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tumLayout = itemView as ConstraintLayout
        var not = tumLayout.tvUniteNotu
        var tarih = tumLayout.tvUniteNotuTarih
        var sil = tumLayout.imgDriveUniteNotDelete



        fun setData(gelenUniteNotListesi: UniteNotuModel, position: Int) {

            not.setText(gelenUniteNotListesi.uniteNotu)
            tarih.setText(gelenUniteNotListesi.uniteNotuTarih)

            sil.setOnClickListener {

                //AlertDialog Penceresi------------------------------
                val builder = AlertDialog.Builder(mContext)
                builder.setTitle("Seçimi Sil?")
                builder.setMessage("${gelenUniteNotListesi.uniteNotuTarih} Tarihli Notu Silmek İstiyor Musunuz?")

                builder.setPositiveButton("EVET", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                        ref.child(motorTag)
                            .child(gelenUniteNotListesi.sistemSaat)
                            .removeValue()

                        uniteNotListesi.removeAt(position)
                        notifyDataSetChanged()
//                        notifyItemRemoved(position)
//                        notifyItemRangeChanged(position,uniteNotListesi.size)

                        Toast.makeText(mContext,"${gelenUniteNotListesi.uniteNotuTarih} Silindi!",Toast.LENGTH_SHORT).show()
                    }


                })

                builder.setNegativeButton("HAYIR", object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(mContext,"Seçim Silinmedi!", Toast.LENGTH_SHORT).show()
                    }
                })

                val dialog : AlertDialog =builder.create()
                dialog.show()
            }
        }
    }
}
