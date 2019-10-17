package com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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


class AmbarKayit : Fragment() , SwipeRefreshLayout.OnRefreshListener {

    var ambarListe = ArrayList<AmbarKayitModeli>()
    lateinit var mFAB_ambar: FloatingActionButton
    lateinit var myAdapter : AmbarRV
    lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ambar_kayit, container, false)

        swipeRefresh = view.findViewById(R.id.swipeRefresh)

        fireBaseDBOkunanVeriler(view.context)

        swipeRefresh.setOnRefreshListener(this)

        val kayitAra = view.findViewById<EditText>(R.id.etAmbarKayitAra)

        kayitAra.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {

                if (p0 != null) {

                    val gelenVeri = p0.toString().toUpperCase()
                    val arananlar = ArrayList<AmbarKayitModeli>()

                    for (gelen in ambarListe) {

                        val bulunanStokNo = gelen.ambarStokNo.toUpperCase()
                        val bulunanTanim = gelen.ambarTanim
                        if (bulunanStokNo.contains(gelenVeri)) {
                            arananlar.add(gelen)
                        } else if (bulunanTanim.contains(gelenVeri))
                            arananlar.add(gelen)
                    }
                    if (myAdapter != null) {
                        myAdapter.gelenAmbarKaydiFiltrele(arananlar)
                    }
                }

            }

        })

        mFAB_ambar = view.findViewById(R.id.menu_ambar)
        mFAB_ambar.setOnClickListener {
            val ambarFragment = AmbarKayitEkleme()
            ambarFragment.show(fragmentManager!!,"ambar_ekle_fragmenti")
        }



        return view
    }

    private fun fireBaseDBOkunanVeriler(context: Context) {

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
                    recyclerAdapter(ambarListe , context,fragmentManager)

                }

            })
    }


    private fun recyclerAdapter(ambarGelenListe: ArrayList<AmbarKayitModeli>, mContext: Context,fragmentManager: FragmentManager?) {

        myAdapter = AmbarRV(ambarGelenListe,mContext ,fragmentManager)
        view?.rvAmbarListe?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL,false)
        view?.rvAmbarListe?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()
    }

    override fun onRefresh() {

        fireBaseDBOkunanVeriler(view!!.context)

        Handler().postDelayed(object : Runnable{
            override fun run() {
                swipeRefresh.isRefreshing = false
            }
        },1200)

    }
}
