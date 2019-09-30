package com.example.pm3elektrik.MotorListeSayfasi.CekmecesiSalterOlanSayfa


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.CekmecesiSalterOlanSayfa.CekmesiSalterOlanModel.CekmeceSalterModel
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.CekmeceEkle
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel

import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CekmecesiSalterOlanEtiket : Fragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Motor")
    var uniqIDGelen: String? = null
    val bilgiYok = "Bilgi Yok"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cekmecesi_salter_olan_etiket, container, false)

        val close = view.findViewById<ImageView>(R.id.imgSalterOlanEtiketClose)
        val duzenle = view.findViewById<ImageView>(R.id.imgSalterOlanEtiketDuzenle)

        close.setOnClickListener {

            if(view != null){
                changeFragment(MotorListe())
            }
        }

        duzenle.setOnClickListener {

            val bundle : Bundle? =Bundle()
            bundle?.putString("salterOlanCekmecedenGelen",uniqIDGelen)
            val fragment = CekmeceEkle()
            fragment.arguments = bundle
            val transaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.containerCekmeceSalterOlan,fragment,"cekmece_salter_olan_fr")?.commit()
        }

        val bundle :Bundle? = arguments
        uniqIDGelen = bundle?.getString("rvGelenTag")
        Log.e("gelenUniqID",uniqIDGelen)


        firebaseOkunanVerileriTextViewIsle(view)
        return view
    }

    private fun firebaseOkunanVerileriTextViewIsle(view: View) {

        ref.child(uniqIDGelen!!)
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    if (p0.getValue() != null){
                        val okunan = p0.getValue(MotorModel::class.java)
                            if (okunan != null){
                                gelenBilgileriTexteIsle(okunan,view)
                            }
                    }
                }
            })


    }

    private fun gelenBilgileriTexteIsle(okunan: MotorModel, view: View) {

        val isim = view.findViewById<TextView>(R.id.tvSalterOlanEtiketIsim)
        val marka = view.findViewById<TextView>(R.id.tvSalterOlanMarka)
        val model = view.findViewById<TextView>(R.id.tvSalterOlanModel)
        val cat = view.findViewById<TextView>(R.id.tvSalterOlanCat)
        val kapasite = view.findViewById<TextView>(R.id.tvSalterOlanKapasite)
        val demeraj = view.findViewById<TextView>(R.id.tvSalterOlanDemeraj)
        val mccYeri = view.findViewById<TextView>(R.id.tvSalterOlanMccYeri)
        val degTarihi  = view.findViewById<TextView>(R.id.tvSalterOlanDegTarihi)

        isim.setText(okunan.cekmeceIsim)
        mccYeri.setText(okunan.motorMCCYeri)

        //--------marka-------------------------
        if (okunan.cekmeceMarka.isNullOrBlank()){
            marka.setText(bilgiYok)
        }else{marka.setText(okunan.cekmeceMarka)}

        //--------model-------------------------
        if (okunan.cekmeceModel.isNullOrBlank()){
            model.setText(bilgiYok)
        }else{model.setText(okunan.cekmeceModel)}

        //--------cat-------------------------
        if (okunan.cekmeceCat.isNullOrBlank()){
            cat.setText(bilgiYok)
        }else{cat.setText(okunan.cekmeceCat)}

        //--------kapasite-------------------------
        if (okunan.cekmeceKapasite.isNullOrBlank()){
            kapasite.setText(bilgiYok)
        }else{kapasite.setText(okunan.cekmeceKapasite + " A")}

        //--------demeraj-------------------------
        if (okunan.cekmeceDemeraj.isNullOrBlank()){
            demeraj.setText(bilgiYok)
        }else{demeraj.setText(okunan.cekmeceDemeraj)}

        //--------değişim tarihi-------------------------
        if (okunan.cekmeceSalterDegisim.isNullOrBlank()){
            degTarihi.setText(bilgiYok)
        }else{degTarihi.setText(okunan.cekmeceSalterDegisim)}


    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"cekmece_ekle_fr")
        fragmentTransaction.commit()

    }


}
