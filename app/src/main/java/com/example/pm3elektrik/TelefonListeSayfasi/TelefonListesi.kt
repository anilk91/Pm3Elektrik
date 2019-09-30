package com.example.pm3elektrik.TelefonListeSayfasi


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
    var telefonModel = ArrayList<TelefonListeModel>()
    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
    lateinit var myAdapter : TelefonRV

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_telefon_listesi, container, false)

        fireBaseDBOkunanVeriler(view.context , view)

        mFAB_telefon = view.findViewById(R.id.menu_telefon)
        mFAB_telefon.setOnClickListener {

            val telefonEkle = TelefonEkle()
            telefonEkle.show(fragmentManager!!,"telefon_ekle_dialog_fr")

        }

        val telefonIsimAra = view.findViewById<EditText>(R.id.etTelefonListeArama)

        telefonIsimAra.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {

                if (p0 != null) {

                    val gelenVeri = p0.toString().toUpperCase()
                    val arananlar = ArrayList<TelefonListeModel>()

                    for (gelen in telefonModel) {

                        val bulunanTelIsim = gelen.telefonIsim.toUpperCase()
                        val bulunanTelNo = gelen.telefonNo
                        if (bulunanTelIsim.contains(gelenVeri)) {
                            arananlar.add(gelen)
                        }
                        else if(bulunanTelNo.contains(gelenVeri))
                            arananlar.add(gelen)
                    }
                    if (myAdapter != null) {
                        myAdapter.gelenTelefonIsmiFiltrele(arananlar)
                    }
                }

            }


        })

        return view
    }

    fun fireBaseDBOkunanVeriler(mContext: Context, view: View){

        ref.child("Telefon")
            .orderByKey()
            .addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    for(gelenVeri in p0.children){

                        val ekle = gelenVeri.getValue(TelefonListeModel::class.java)
                        telefonModel.add(TelefonListeModel(ekle!!.telefonIsim,ekle.telefonNo))
                    }

                    recyclerAdapter(telefonModel ,view,mContext)
                }


            })

    }


    //FirebaseDatabase Okunan Veriler Recycler Adapter'a Yollanıyor----------------------->
    fun recyclerAdapter(telefonGelenListe: ArrayList<TelefonListeModel>, view: View, mContext: Context){

        myAdapter = TelefonRV(telefonGelenListe,fragmentManager,mContext)
        view.rvTelefonListe?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mContext,RecyclerView.VERTICAL,false)
        view.rvTelefonListe?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()
    }
    //FirebaseDatabase Okunan Veriler Recycler Adapter'a Yollanıyor-----------------------<
}
