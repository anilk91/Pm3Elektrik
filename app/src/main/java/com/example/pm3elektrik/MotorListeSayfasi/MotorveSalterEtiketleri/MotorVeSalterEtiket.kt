package com.example.pm3elektrik.MotorListeSayfasi.MotorveSalterEtiketleri


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.DuzenleFragmentleri.MotorEtiketDuzenle
import com.example.pm3elektrik.MotorListeSayfasi.MotorInterface.MotorTagInterface
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SalterModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SurucuModel

import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_motor_ve_salter_etiket.*


class MotorVeSalterEtiket : Fragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
    lateinit var motorTag: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_motor_ve_salter_etiket, container, false)


        val motorSalterEtiketClose = view.findViewById<ImageView>(R.id.imgMotorSalterClose)
        val motorEdit = view.findViewById<ImageView>(R.id.imgEditMotorBilgi)
        val salterSurucuEdit = view.findViewById<ImageView>(R.id.imgEditSalterSurucu)

        motorSalterEtiketClose.setOnClickListener {

        }
        motorEdit.setOnClickListener {

            (activity as MotorTagInterface).gelenMotorTag(motorTag)
            changeFragment(MotorEtiketDuzenle())

        }


        return view
    }

    private fun firebaseDBOku() {



        //-----------Şalter Etiket Bilgilerini Getir--------------
        ref.child("Salter")
            .child(motorTag)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    salterEtiketBilgileriGelen(p0)
                }
            })

        //-----------Motor Bilgilerini Getir--------------
        ref.child("Motor")
            .child(motorTag)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    motorEtiketBilgileriGelen(p0)
                }
            })


        //-----------Sürücü Bilgilerini Getir--------------
        ref.child("Surucu")
            .child(motorTag)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    surucuEtiketBilgileriGelen(p0)

                }
            })

    }

    private fun surucuEtiketBilgileriGelen(p0: DataSnapshot) {

        val surucuBilgileriGetir = p0.getValue(SurucuModel::class.java)


        if (surucuBilgileriGetir != null) {
            if (surucuBilgileriGetir.surucuBoyut.isNotEmpty() && surucuBilgileriGetir.surucuDIPSivic.isNotEmpty()) {

                tvSurucuEtiketKonBoyut.text = (surucuBilgileriGetir.surucuBoyut)
                tvSurucuEtiketKonDip.text = (surucuBilgileriGetir.surucuDIPSivic)
                tvSurucuEtiketTipi.text = (surucuBilgileriGetir.surucuIsim)
                tvSurucuEtiketModel.text = (surucuBilgileriGetir.surucuModel)
                tvSurucuEtiketDegTarihi.text = (surucuBilgileriGetir.surucuDegTarihi)
            } else if (surucuBilgileriGetir.surucuBoyut.isEmpty() && surucuBilgileriGetir.surucuDIPSivic.isEmpty()) {

                tvSurucuEtiketKonBoyut.visibility = View.GONE
                tvSurucuEtiketKonDip.visibility = View.GONE
                tvBoyutYazisi.visibility = View.GONE
                tvDipSivicYazisi.visibility = View.GONE

                tvSurucuEtiketTipi.text = surucuBilgileriGetir.surucuIsim
                tvSurucuEtiketModel.text = (surucuBilgileriGetir.surucuModel)
                tvSurucuEtiketDegTarihi.text = surucuBilgileriGetir.surucuDegTarihi

            } else {
                tvSurucuEtiketKonBoyut.setText(surucuBilgileriGetir.surucuBoyut)
                tvSurucuEtiketKonDip.setText(surucuBilgileriGetir.surucuDIPSivic)
                tvSurucuEtiketTipi.setText(surucuBilgileriGetir.surucuIsim)
                tvSurucuEtiketModel.setText(surucuBilgileriGetir.surucuModel)
                tvSurucuEtiketDegTarihi.setText(surucuBilgileriGetir.surucuDegTarihi)
            }
        } else {
        }
    }

    private fun salterEtiketBilgileriGelen(p0: DataSnapshot) {

        val cekmeceBilgiGetir = p0.getValue(SalterModel::class.java)


        if (cekmeceBilgiGetir != null) {
            tvSalterMarka.text = (cekmeceBilgiGetir.salterMarka)
            tvSalterKapasite.text = (cekmeceBilgiGetir.salterKapasite)
            tvSalterEtiketCat.text = (cekmeceBilgiGetir.salterCAT)
            tvSalterEtiketStyle.text = (cekmeceBilgiGetir.salterSTYLE)
            tvSalterEtiketDemeraj.text = (cekmeceBilgiGetir.salterDemeraj)
            tvSalterEtiketDegTarihi.text = (cekmeceBilgiGetir.salterDegTarihi)
        } else {
        }


    }

    private fun motorEtiketBilgileriGelen(p0: DataSnapshot) {


        val motorBilgiGetir = p0.getValue(MotorModel::class.java)

        if (motorBilgiGetir != null) {

            tvMotorEtiketPompaIsim.text = (motorBilgiGetir.motorIsim)
            tvMotorEtiketTag.text = (motorBilgiGetir.motorTag)
            tvMotorEtiketDevir.text = (motorBilgiGetir.motorDevir + " d/d")
            tvMotorEtiketNomTripAkim.text = (motorBilgiGetir.motorNomTripAkimi + " A")
            tvMotorEtiketInsaTipi.text = (motorBilgiGetir.motorInsaTipi)
            tvMotorEtiketFlans.text = (motorBilgiGetir.motorFlans)
            tvMotorEtiketAdres.text = (motorBilgiGetir.motorAdres)
            tvMotorEtiketMccYeri.text = (motorBilgiGetir.motorMCCYeri)
            tvMotorEtiketDegTarih.text = (motorBilgiGetir.motorDegTarihi)
            tvMotorEtiketGucKw.text = ("${motorBilgiGetir.motorGucKW} KW")
            tvMotorEtiketGucHp.text = ("${motorBilgiGetir.motorGucHP} HP")
        }
        else { }
    }

    fun recyclerAdapterGelenTag(motorTag : String) {
        Log.e("MotorVeSalter","$motorTag")
        this.motorTag = motorTag
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorSalterEtiket,fragment,"fragment_motor_salter_etiket")
        fragmentTransaction.commit()

    }


}
