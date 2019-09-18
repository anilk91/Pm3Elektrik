package com.example.pm3elektrik.DigerBilgilerSayfasi.KuyuPanoSayfasi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.DigerBilgilerSayfasi.KuyuPanoSayfasi.KuyuPanoModel.KuyuPanoModel
import com.example.pm3elektrik.DigerBilgilerSayfasi.KuyuPanoSayfasi.KuyuPanoRVAdapter.RVKuyuPano

import com.example.pm3elektrik.R
import kotlinx.android.synthetic.main.fragment_kuyu_panolari.view.*

class KuyuPanolari : Fragment() {

    val kuyuListe = ArrayList<KuyuPanoModel>()
    lateinit var myAdapter : RVKuyuPano

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kuyu_panolari, container, false)

        kuyuGelenListe()
        myAdapter = RVKuyuPano(kuyuListe,view.context)
        view.rvKuyuListe.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(view.context,RecyclerView.VERTICAL,false)
        view.rvKuyuListe.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()


        return view
    }

    private fun kuyuGelenListe() {

        kuyuListe.add(KuyuPanoModel("1 NOLU KUYU","Mazot İstasyonu Yanı","Yıldız - Üçgen Kontaktör","Bilgi Yok","Mcc 6/7 7.7 Ön"))
        kuyuListe.add(KuyuPanoModel("4 NOLU KUYU","Arıtma","Yıldız - Üçgen Kontaktör","Bilgi Yok","Arıtma"))
        kuyuListe.add(KuyuPanoModel("5 NOLU KUYU","Araç Bakım Karşısı","Yıldız - Üçgen Kontaktör","Bilgi Yok","Jeneratör Dairesi"))
        kuyuListe.add(KuyuPanoModel("6 NOLU KUYU","Mekanik Atölye Arkası","Siemens Easy Soft Starter","Bilgi Yok","Mcc 6/7 7.7 Ön"))
        kuyuListe.add(KuyuPanoModel("8 NOLU KUYU","PM4 Güvenlik Girişi","Yıldız - Üçgen Kontaktör","Bilgi Yok","Jeneratör Dairesi"))
        kuyuListe.add(KuyuPanoModel("9 NOLU KUYU","Jeneratör Hizasında Paratoner Direği Yanı","Yıldız - Üçgen Kontaktör","Bilgi Yok","Jeneratör Dairesi"))
        kuyuListe.add(KuyuPanoModel("10 NOLU KUYU","4 Nolu Kapı Yanı","Yıldız - Üçgen Kontaktör","Bilgi Yok","Jeneratör Dairesi"))

    }


}
