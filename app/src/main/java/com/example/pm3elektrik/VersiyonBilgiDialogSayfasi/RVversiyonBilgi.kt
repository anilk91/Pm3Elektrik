package com.example.pm3elektrik.VersiyonBilgiDialogSayfasi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.rv_versiyon_bilgi.view.*

class RVversiyonBilgi (var versiyonAciklama: ArrayList<VersiyonBilgiModel>) : RecyclerView.Adapter<RVversiyonBilgi.MyData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyData {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_versiyon_bilgi,parent,false)

        return MyData(view)
    }

    override fun getItemCount(): Int {
        return versiyonAciklama.size
    }

    override fun onBindViewHolder(holder: RVversiyonBilgi.MyData, position: Int) {
        holder.setData(versiyonAciklama.get(position),position)
    }

    inner class MyData (itemView : View) :RecyclerView.ViewHolder(itemView) {

        var tumLayout = itemView as ConstraintLayout
        var guncelleme = tumLayout.tvGuncellemeBilgileri

        fun setData(gelenGuncelleme : VersiyonBilgiModel , position: Int){

            guncelleme.setText(gelenGuncelleme.versiyonAciklama)

            Log.e("gelenBilgiler","${gelenGuncelleme.versiyonAciklama.get(1)}")

        }

    }


}
