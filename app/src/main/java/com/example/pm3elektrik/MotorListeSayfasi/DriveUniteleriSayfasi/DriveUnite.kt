package com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveRVAdapters.DriveUniteNotlariRV
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel.DriveModel
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel.UniteNotuModel
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.UniteNotEkle.DriveUniteNotEkle
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe

import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_drive_unite.*
import kotlinx.android.synthetic.main.fragment_drive_unite.view.*

class DriveUnite : Fragment() {

    var uniteNotListe = ArrayList<UniteNotuModel>()
    lateinit var myAdapter : DriveUniteNotlariRV
    var motorTag: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drive_unite, container, false)

        val close = view.findViewById<ImageView>(R.id.imgDriveEtiketClose)
        val notEkle = view.findViewById<ImageView>(R.id.imgDriveUniteNotEkle)


        close.setOnClickListener {

            changeFragment(MotorListe())
        }

        val bundle: Bundle? = arguments
        motorTag = bundle?.getString("rvGelenMotorTag")

        notEkle.setOnClickListener {

            val bundleNotEkle : Bundle? =Bundle()
            bundleNotEkle?.putString("driveUniteGelenTag",motorTag)
            val fragment = DriveUniteNotEkle()
            fragment.arguments = bundleNotEkle
            fragment.show(fragmentManager!!,"drive_unite_dialog_fr")

        }

        firebaseDatabaseOkunanNotlar(view.context,view)
        firebaseDatabaseOkunanMotorBilgi(view.context,view)

        return view
    }

    private fun firebaseDatabaseOkunanMotorBilgi(mContext: Context?, view: View) {

        FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Drive").child(motorTag!!)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    if(p0 != null){

                        val gelen : DriveModel = p0.getValue(DriveModel::class.java) as DriveModel

                        tvDriveMotorIsim.setText(gelen.isim)
                        tvDriveMotorTag.setText(gelen.tag)
                        tvDriveMotorGuc.setText(gelen.guc)
                        tvDriveMotorDevir.setText(gelen.devir)
                        tvDriveMotorTripAkim.setText(gelen.tripAkim)
                        tvDriveMotorInsaTipi.setText(gelen.insaTipi)
                        tvDriveMotorFlans.setText(gelen.flans)
                        tvDriveMotorDegTarihi.setText(gelen.motorDegTarihi)
                        tvDriveMotorAdres.setText(gelen.adres)
                        tvDriveMotorAdres.setText(gelen.adres)
                        tvDriveUniteGuc.setText(gelen.uniteGucKVA + " KVA")

                        if (gelen.uniteGucKVA == "20" || gelen.uniteGucKVA == "60" || gelen.uniteGucKVA == "180" || gelen.uniteGucKVA == "250" || gelen.uniteGucKVA == "490"){

                            tvSNoYazisiW.visibility = View.GONE
                            tvDegTarihiW.visibility = View.GONE
                            tvSNoYazisiV.visibility = View.GONE
                            tvDegTarihiV.visibility = View.GONE
                            tvDriveUniteSeriNoV.visibility = View.GONE
                            tvDriveUniteSeriNoW.visibility = View.GONE
                            tvDriveUniteDegTarihiV.visibility = View.GONE
                            tvDriveUniteDegTarihiW.visibility = View.GONE

                            tvSNoYazisiU.setText("S. No :")
                            tvDriveUniteSeriNoU.setText(gelen.seriNoU)
                            tvDriveUniteDegTarihiU.setText(gelen.uModulDegTarihi)

                        }else if (gelen.uniteGucKVA == "600" || gelen.uniteGucKVA == "900" || gelen.uniteGucKVA == "1040" || gelen.uniteGucKVA == "1380"){

                            tvDriveUniteSeriNoU.setText(gelen.seriNoU)
                            tvDriveUniteSeriNoV.setText(gelen.seriNoV)
                            tvDriveUniteSeriNoW.setText(gelen.seriNoW)

                            tvDriveUniteDegTarihiU.setText(gelen.uModulDegTarihi)
                            tvDriveUniteDegTarihiV.setText(gelen.vModulDegTarihi)
                            tvDriveUniteDegTarihiW.setText(gelen.wModulDegTarihi)

                        }


                    }
                }


            })

    }

    private fun firebaseDatabaseOkunanNotlar(mContext: Context, view: View) {


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

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.containerMotorListe,fragment,"fragment_drive_unite_etiket")
        fragmentTransaction?.commit()
    }


}
