package com.example.pm3elektrik.TelefonListeSayfasi.TelefonRVAdapter

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
import com.example.pm3elektrik.R
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonEkleFragment.TelefonEkle
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonModel.TelefonListeModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.telefon_rv_adapter.view.*

class TelefonRV(var telefonListe: ArrayList<TelefonListeModel>, var fragmentManager: FragmentManager?, var mContext: Context) : RecyclerView.Adapter<TelefonRV.MyData>() {


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
        val telefonBilgi = tumLayout.imgTelefonBilgi as ImageView
        val telefonListeSil = tumLayout.imgTelefonListeSil as ImageView

        fun setData(telefon : TelefonListeModel , position: Int){

            telefonIsim.setText(telefon.telefonIsim)
            telefonNo.setText(telefon.telefonNo)

            telefonBilgi.setOnClickListener{

                val bundle : Bundle? = Bundle()
                bundle?.putString("rvGidenTelIsim",telefonListe[position].telefonIsim)
                bundle?.putString("rvGidenTelNo",telefonListe[position].telefonNo)
                val fragment = TelefonEkle()
                fragment.arguments = bundle
                fragment.show(fragmentManager!!,"telefon_ekle_dialog_fr")
            }

            telefonListeSil.setOnClickListener {

                val builder = AlertDialog.Builder(mContext)
                builder.setTitle("Seçimi Sil?")
                builder.setMessage("${telefon.telefonNo} Nolu Telefon Kaydını Silmek İstiyor Musunuz?")

                builder.setPositiveButton("EVET",object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                            .child("Telefon")
                            .child(telefonListe[position].telefonNo)
                            .removeValue()

                        telefonListe.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position,telefonListe.size)
                    }
                })

                builder.setNegativeButton("HAYIR",object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(mContext,"Seçim Silinmedi",Toast.LENGTH_SHORT).show()
                    }
                })

                val dialog : AlertDialog =builder.create()
                dialog.show()

            }
        }


    }

    fun gelenTelefonIsmiFiltrele(telefonIsim: ArrayList<TelefonListeModel>) {

        telefonListe = ArrayList<TelefonListeModel>()
        telefonListe.addAll(telefonIsim)
        notifyDataSetChanged()
    }
}