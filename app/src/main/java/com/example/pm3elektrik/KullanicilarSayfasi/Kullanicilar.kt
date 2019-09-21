package com.example.pm3elektrik.KullanicilarSayfasi


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.KullanicilarSayfasi.KullaniciModel.KullanicilarModel
import com.example.pm3elektrik.KullanicilarSayfasi.RVKullaniciAdapter.RVKullanicilar

import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_kullanicilar.view.*

class Kullanicilar : Fragment() {

    val kullaniciListe = ArrayList<KullanicilarModel>()
    lateinit var myAdapter : RVKullanicilar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kullanicilar, container, false)

        firebaseDatabaseOku(view.context , view)

        return view
    }

    private fun firebaseDatabaseOku(mContext: Context , view: View) {

        FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar").orderByKey()
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    for (gelenVeri in p0.children){

                        val okunan = gelenVeri.getValue(KullanicilarModel::class.java)
                        if (okunan != null){

                            kullaniciListe.add(KullanicilarModel(okunan.isim,okunan.sicilNo))
                        }
                    }

                    recyclerViewFirebasedenOkunanVeriler(kullaniciListe, mContext, view)
                }


            })

    }

    private fun recyclerViewFirebasedenOkunanVeriler(gelenKullaniciListe: ArrayList<KullanicilarModel>, mContext: Context, view: View) {

        myAdapter = RVKullanicilar(gelenKullaniciListe,mContext)
        view.rvKullanicilarListesi.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(view.context,RecyclerView.VERTICAL,false)
        view.rvKullanicilarListesi.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()

    }


}
