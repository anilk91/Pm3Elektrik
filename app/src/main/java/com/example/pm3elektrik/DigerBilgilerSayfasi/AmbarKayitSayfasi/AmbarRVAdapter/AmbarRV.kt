package com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarRVAdapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitEkle.AmbarKayitEkleme
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitModel.AmbarKayitModeli
import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.ambar_rv_adapter.view.*

class AmbarRV(var ambarListe: ArrayList<AmbarKayitModeli>, var mContext: Context, var fragmentManager: FragmentManager?, ambarKaydiDuzenlemeYetkisi: String) :
    RecyclerView.Adapter<AmbarRV.MyData>() {

    var ambarKayitYetkisi = ambarKaydiDuzenlemeYetkisi

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

                    if (ambarKayitYetkisi == "var") {
                        val bundle: Bundle? = Bundle()
                        bundle?.putString("rvGidenStokNo", ambarListe[position].ambarStokNo)
                        bundle?.putString("rvGidenRafNo", ambarListe[position].ambarRafNo)
                        bundle?.putString("rvGidenTanim", ambarListe[position].ambarTanim)
                        val fragment = AmbarKayitEkleme()
                        fragment.arguments = bundle
                        fragment.show(fragmentManager!!, "ambar_kayit_ekle_fr")
                    }else{
                        Toast.makeText(mContext,"Ambar Kaydını Düzenleme Yetkiniz Yok!", Toast.LENGTH_SHORT).show()
                    }
                }

                silButonu.setOnClickListener {

                    if (ambarKayitYetkisi == "var") {
                        val builder = AlertDialog.Builder(mContext)
                        builder.setTitle("Seçimi Sil?")
                        builder.setMessage("${ambarListeSetData.ambarStokNo} Nolu Ambar Kaydını Silmek İstiyor Musunuz?")
                        builder.setPositiveButton("EVET", object : DialogInterface.OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                val stokNo = ambarListe[position].ambarStokNo
                                val s1 = stokNo.substring(0..0)
                                val s2 = stokNo.substring(2..stokNo.lastIndex)
                                val stokNoSonHal = s1 + s2

                                FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                                    .child("Ambar")
                                    .child(stokNoSonHal)
                                    .removeValue()

                                ambarListe.removeAt(position)
                                notifyItemRemoved(position)
                                notifyItemRangeChanged(position, ambarListe.size)

                                Toast.makeText(
                                    mContext,
                                    "${ambarListeSetData.ambarStokNo} Silindi!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        })

                        builder.setNegativeButton(
                            "HAYIR",
                            object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    Toast.makeText(mContext, "Seçim Silinmedi", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            })

                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                    }else{
                        Toast.makeText(mContext,"Ambar Kaydını Silme Yetkiniz Yok!", Toast.LENGTH_SHORT).show()
                    }
                }




        }
    }
    fun gelenAmbarKaydiFiltrele(arananlar: java.util.ArrayList<AmbarKayitModeli>) {


        ambarListe = ArrayList<AmbarKayitModeli>()
        ambarListe.addAll(arananlar)
        notifyDataSetChanged()

    }
}
