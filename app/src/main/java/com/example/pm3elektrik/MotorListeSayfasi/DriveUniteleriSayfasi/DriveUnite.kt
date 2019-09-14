package com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveRVAdapters.DriveUniteNotlariRV
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel.UniteNotuModel
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.UniteNotEkle.DriveUniteNotEkle

import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_drive_unite.view.*

class DriveUnite : Fragment() {

    var uniteNotListe = ArrayList<UniteNotuModel>()
    lateinit var myAdapter : DriveUniteNotlariRV
    var motorTag: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drive_unite, container, false)
        val notEkle = view.findViewById<ImageView>(R.id.imgDriveUniteNotEkle)

        val bundle: Bundle? = arguments
        motorTag = bundle?.getString("rvGelenMotorTag")

        notEkle.setOnClickListener {

            val bundle : Bundle? =Bundle()
            bundle?.putString("driveUniteGelenTag",motorTag)
            val fragment = DriveUniteNotEkle()
            fragment.arguments = bundle
            fragment.show(fragmentManager!!,"drive_unite_dialog_fr")

        }

        firebaseDatabaseOkunan(view.context,view)

        return view
    }

     private fun firebaseDatabaseOkunan(mContext: Context, view: View) {

         Log.e("gelenTag","$motorTag")
        FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("DriveUniteNot").child(motorTag!!)
            .addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {

                if (p0 != null){

                    for (gelen in p0.children){

                        val okunan = gelen.getValue(UniteNotuModel::class.java)

                        uniteNotListe.add(UniteNotuModel(okunan!!.uniteNotu,okunan!!.uniteNotuTarih , okunan!!.sistemSaat))
                    }
                    firebaseGelenRecyclerView(uniteNotListe,mContext ,view)
                }
            }
        })

    }

     private fun firebaseGelenRecyclerView(uniteNotListe: ArrayList<UniteNotuModel>, mContext: Context, view: View) {

        myAdapter = DriveUniteNotlariRV(uniteNotListe,mContext,motorTag!!)
        view.rvUniteNotlariListesi?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mContext,RecyclerView.VERTICAL,false)
        view.rvUniteNotlariListesi?.layoutManager = mLayoutManager

        myAdapter.notifyDataSetChanged()
    }
}
