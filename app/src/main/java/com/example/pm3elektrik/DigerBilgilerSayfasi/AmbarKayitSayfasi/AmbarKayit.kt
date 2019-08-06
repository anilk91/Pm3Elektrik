package com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitEkle.AmbarKayitEkleme
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitModel.AmbarKayitModeli
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarRVAdapter.AmbarRV

import com.example.pm3elektrik.R
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_ambar_kayit.view.*


class AmbarKayit : Fragment() {

    val ambarListe = ArrayList<AmbarKayitModeli>()
    lateinit var mFAB_ambar: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ambar_kayit, container, false)

        val ambarAra = view.findViewById<EditText>(R.id.etAmbarKayitAra)
        mFAB_ambar = view.findViewById(R.id.menu_ambar)
        fireBaseDBOkunanVeriler()

        mFAB_ambar.setOnClickListener {
            val ambarFragment = AmbarKayitEkleme()
            ambarFragment.show(fragmentManager,"ambar_ekle_fragmenti")
        }



        return view
    }

    private fun fireBaseDBOkunanVeriler() {

        val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
        ref.child("Ambar")
            .orderByKey()
            .addValueEventListener( object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    for(dataGetir in p0.children){

                        val okunanBilgiler = dataGetir.getValue(AmbarKayitModeli::class.java)

                        ambarListe.add(AmbarKayitModeli(okunanBilgiler!!.ambarStokNo,okunanBilgiler.ambarRafNo,okunanBilgiler.ambarTanim))
                    }
                    recyclerAdapter(ambarListe)

                }

            })
    }


    private fun recyclerAdapter(ambarGelenListe : ArrayList<AmbarKayitModeli>) {

        val mContext = view?.context as Context
        val myAdapter = AmbarRV(ambarGelenListe,mContext)
        view?.rvAmbarListe?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL,false)
        view?.rvAmbarListe?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()

    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerAmbarKayit,fragment,"fragment")
        fragmentTransaction.commit()

    }


}
