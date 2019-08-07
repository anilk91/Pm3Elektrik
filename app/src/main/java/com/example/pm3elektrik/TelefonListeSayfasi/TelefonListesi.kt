package com.example.pm3elektrik.TelefonListeSayfasi


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.pm3elektrik.R
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonEkleFragment.TelefonEkle
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonModel.TelefonListeModel
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonRVAdapter.TelefonRV
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_telefon_listesi.view.*

class TelefonListesi : Fragment() {

    lateinit var mFAB_telefon: FloatingActionButton
    val telefonModel = ArrayList<TelefonListeModel>()
    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_telefon_listesi, container, false)

        fireBaseDBOkunanVeriler()

        mFAB_telefon = view.findViewById(R.id.menu_telefon)
        mFAB_telefon.setOnClickListener {

            val telefonEkle = TelefonEkle()
            telefonEkle.show(fragmentManager,"telefon_ekle_dialog_fr")

        }

        return view
    }

    private fun fireBaseDBOkunanVeriler(){

        ref.child("Telefon")
            .orderByKey()
            .addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    for(gelenVeri in p0.children){

                        val ekle = gelenVeri.getValue(TelefonListeModel::class.java)
                        telefonModel.add(TelefonListeModel(ekle!!.telefonIsim,ekle.telefonNo))
                    }

                    recyclerAdapter(telefonModel)
                }


            })

    }

    private fun recyclerAdapter(telefonGelenListe : ArrayList<TelefonListeModel>){

        val mContext = view?.context as Context
        val mAdapter = TelefonRV(telefonGelenListe)
        view?.rvTelefonListe?.adapter = mAdapter

        val mLayoutManager = LinearLayoutManager(mContext,RecyclerView.VERTICAL,false)
        view?.rvTelefonListe?.layoutManager = mLayoutManager

        mAdapter.notifyDataSetChanged()


    }

}
