package com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarRVAdapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitEkle.AmbarKayitEkleme
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitModel.AmbarKayitModeli
import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.ambar_rv_adapter.view.*

class AmbarRV(var ambarListe: ArrayList<AmbarKayitModeli>, var mContext: Context , var fragmentManager: FragmentManager?) :
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
        val bilgiButonu = tumLayout.imgAmbarRVBilgiButonu as ImageView
        val silButonu = tumLayout.imgAmbarRVSilButonu as ImageView

        fun setData(ambarListeSetData: AmbarKayitModeli, position: Int) {

            stokNo.setText(ambarListeSetData.ambarStokNo)
            rafNo.setText(ambarListeSetData.ambarRafNo)
            tanim.setText(ambarListeSetData.ambarTanim)

            bilgiButonu.setOnClickListener {

                val bundle : Bundle? = Bundle()
                bundle?.putString("rvGidenStokNo",ambarListe[position].ambarStokNo)
                bundle?.putString("rvGidenRafNo",ambarListe[position].ambarRafNo)
                bundle?.putString("rvGidenTanim",ambarListe[position].ambarTanim)
                val fragment = AmbarKayitEkleme()
                fragment.arguments = bundle
                fragment.show(fragmentManager,"ambar_kayit_ekle_fr")

            }

            silButonu.setOnClickListener {

                val stokNo = ambarListe[position].ambarStokNo
                val s1 = stokNo.substring(0..0)
                val s2 = stokNo.substring(2..stokNo.lastIndex)
                val stokNoSonHal = s1+s2

                FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                    .child("Ambar")
                    .child(stokNoSonHal)
                    .removeValue()

                ambarListe.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,ambarListe.size)

            }
        }
    }
}
