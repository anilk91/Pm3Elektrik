package com.example.pm3elektrik.MotorListeSayfasi.MotorveSalterEtiketleri


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.DuzenleFragmentleri.CekmeceEtiketDuzenle
import com.example.pm3elektrik.MotorListeSayfasi.DuzenleFragmentleri.MotorEtiketDuzenle
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
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
    var motorTag: String? = null
    val bilgiYok = "Bilgi Yok"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_motor_ve_salter_etiket, container, false)

        val bundle: Bundle? = arguments
        motorTag = bundle?.getString("rvGelenMotorTag")

        firebaseDBOku(motorTag)

        val motorSalterEtiketClose = view.findViewById<ImageView>(R.id.imgMotorSalterClose)
        val motorEdit = view.findViewById<ImageView>(R.id.imgEditMotorBilgi)
        val salterSurucuEdit = view.findViewById<ImageView>(R.id.imgEditSalterSurucu)

        motorSalterEtiketClose.setOnClickListener {

            changeFragment(MotorListe())
        }
        motorEdit.setOnClickListener {

            val bundleMotorEtiketDuzenle: Bundle? = Bundle()
            bundleMotorEtiketDuzenle?.putString("motorEtiketDuzenle", motorTag)
            val fragment = MotorEtiketDuzenle()
            fragment.arguments = bundleMotorEtiketDuzenle
            val transaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(
                R.id.containerMotorSalterEtiket,
                fragment,
                "motor_ve_salter_etiket"
            )?.commit()

        }
        salterSurucuEdit.setOnClickListener {

            val bundleCekmeceEtiketDuzenle: Bundle? = Bundle()
            bundleCekmeceEtiketDuzenle?.putString("cekmeceEtiketDuzenle", motorTag)
            val fragment = CekmeceEtiketDuzenle()
            fragment.arguments = bundleCekmeceEtiketDuzenle
            val transaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(
                R.id.containerMotorSalterEtiket,
                fragment,
                "motor_ve_salter_etiket"
            )?.commit()

        }

        return view
    }

    private fun firebaseDBOku(gelenMotorTag: String?) {

        Log.e("motorEtiketGorFB","$motorTag")

        //-----------Şalter Etiket Bilgilerini Getir--------------
        ref.child("Salter")
            .child(gelenMotorTag!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    salterEtiketBilgileriGelen(p0)
                }
            })

        //-----------Motor Bilgilerini Getir--------------
        ref.child("Motor")
            .child(gelenMotorTag)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    motorEtiketBilgileriGelen(p0)
                }
            })


        //-----------Sürücü Bilgilerini Getir--------------
        ref.child("Surucu")
            .child(gelenMotorTag)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    surucuEtiketBilgileriGelen(p0)

                }
            })

    }

    private fun surucuEtiketBilgileriGelen(p0: DataSnapshot) {

        val surucuBilgileriGetir = p0.getValue(SurucuModel::class.java)


        if (surucuBilgileriGetir != null) {
            if (surucuBilgileriGetir.surucuIsim == "Kontaktör") {

                tvSurucuEtiketKonBoyut.text = (surucuBilgileriGetir.surucuBoyut)
                tvSurucuEtiketKonDip.text = (surucuBilgileriGetir.surucuDIPSivic)
                tvSurucuEtiketTipi.text = (surucuBilgileriGetir.surucuIsim)
                tvSurucuEtiketModel.text = (surucuBilgileriGetir.surucuModel)

                if (surucuBilgileriGetir.surucuDegTarihi.isEmpty()){
                    tvSurucuEtiketDegTarihi.text = bilgiYok
                }else{
                    tvSurucuEtiketDegTarihi.text = surucuBilgileriGetir.surucuDegTarihi
                }

            } else {
                tvSurucuEtiketKonBoyut.visibility = View.GONE
                tvSurucuEtiketKonDip.visibility = View.GONE
                tvBoyutYazisi.visibility = View.GONE
                tvDipSivicYazisi.visibility = View.GONE

                tvSurucuEtiketTipi.text = surucuBilgileriGetir.surucuIsim
                tvSurucuEtiketModel.text = (surucuBilgileriGetir.surucuModel)

                if (surucuBilgileriGetir.surucuDegTarihi.isEmpty()){

                    tvSurucuEtiketDegTarihi.text = bilgiYok
                }else{
                    tvSurucuEtiketDegTarihi.text = surucuBilgileriGetir.surucuDegTarihi
                }


            }
        }else{
            Toast.makeText(context?.applicationContext, "Bilgiler Getirilemedi", Toast.LENGTH_LONG).show()
        }
    }

    private fun salterEtiketBilgileriGelen(p0: DataSnapshot) {

        val cekmeceBilgileri = p0.getValue(SalterModel::class.java)

        if (cekmeceBilgileri != null) {
            //-----------------------------
            if (cekmeceBilgileri.salterMarka.isNotEmpty()) {
                tvSalterMarka.text = (cekmeceBilgileri.salterMarka)
            } else {
                tvSalterMarka.text = (bilgiYok)
            }

            //--------------------------------
            if (cekmeceBilgileri.salterKapasite.isEmpty()) {
                tvSalterKapasite.text = (bilgiYok)
            } else {
                tvSalterKapasite.text = (cekmeceBilgileri.salterKapasite + " A")
            }

            //--------------------------------
            if (cekmeceBilgileri.salterCAT.isEmpty()) {
                tvSalterEtiketCat.text = (bilgiYok)
            } else {
                tvSalterEtiketCat.text = (cekmeceBilgileri.salterCAT)
            }
            //------------------------------
            if (cekmeceBilgileri.salterSTYLE.isEmpty()) {
                tvSalterEtiketStyle.text = (bilgiYok)
            } else {
                tvSalterEtiketStyle.text = (cekmeceBilgileri.salterSTYLE)
            }
            //-------------------------------------
            if (cekmeceBilgileri.salterDemeraj.isEmpty()) {
                tvSalterEtiketDemeraj.text = (bilgiYok)
            } else {
                tvSalterEtiketDemeraj.text = (cekmeceBilgileri.salterDemeraj)
            }
            //-----------------------------------
            if (cekmeceBilgileri.salterDegTarihi.isEmpty()) {
                tvSalterEtiketDegTarihi.text = (bilgiYok)
            } else {
                tvSalterEtiketDegTarihi.text = (cekmeceBilgileri.salterDegTarihi)
            }
            //-----------------------------------
            if (cekmeceBilgileri.cekmeceDegTarihi.isEmpty()) {
                tvCekmeceDegTarihi.text = (bilgiYok)
            } else {
                tvCekmeceDegTarihi.text = (cekmeceBilgileri.cekmeceDegTarihi)
            }
        }else{
            Toast.makeText(context?.applicationContext, "Bilgiler Getirilemedi", Toast.LENGTH_LONG).show()
        }

    }

    private fun motorEtiketBilgileriGelen(p0: DataSnapshot) {


        val motorBilgiGetir = p0.getValue(MotorModel::class.java)

        if (motorBilgiGetir != null) {

            if (motorBilgiGetir.motorTag.isNotEmpty()){
                tvMotorEtiketTag.text = (motorBilgiGetir.motorTag)
            }
            if (motorBilgiGetir.motorMCCYeri.isNotEmpty()){
                tvMotorEtiketMccYeri.text = (motorBilgiGetir.motorMCCYeri)
            }

            //------------------------------------------------------------
            if (motorBilgiGetir.motorIsim.isEmpty()){
                tvMotorEtiketPompaIsim.text = bilgiYok
            }else{
                tvMotorEtiketPompaIsim.text = (motorBilgiGetir.motorIsim)
            }

            //------------------------------------------------------------
            if (motorBilgiGetir.motorDevir.isEmpty()){
                tvMotorEtiketDevir.text = bilgiYok
            }else{
                tvMotorEtiketDevir.text = (motorBilgiGetir.motorDevir + " D/d")
            }

            //------------------------------------------------------------
            if(motorBilgiGetir.motorNomTripAkimi.isEmpty()){
                tvMotorEtiketNomTripAkim.text = bilgiYok
            }else{
                tvMotorEtiketNomTripAkim.text = (motorBilgiGetir.motorNomTripAkimi + " A")
            }

            //--------------------------------------------------------------
            if (motorBilgiGetir.motorInsaTipi.isEmpty()){
                tvMotorEtiketInsaTipi.text = bilgiYok
            }else{
                tvMotorEtiketInsaTipi.text = (motorBilgiGetir.motorInsaTipi)
            }

            //----------------------------------------------------------------

            if (motorBilgiGetir.motorFlans.isEmpty()){
                tvMotorEtiketFlans.text = bilgiYok
            }else{
                tvMotorEtiketFlans.text = (motorBilgiGetir.motorFlans)
            }

            //-----------------------------------------------------------------
            if (motorBilgiGetir.motorAdres.isEmpty()){
                tvMotorEtiketAdres.text = bilgiYok
            }else{
                tvMotorEtiketAdres.text = (motorBilgiGetir.motorAdres)
            }

            //---------------------------------------------------------------
            if (motorBilgiGetir.motorDegTarihi.isEmpty()){
                tvMotorEtiketDegTarih.text = bilgiYok
            }else{
                tvMotorEtiketDegTarih.text = (motorBilgiGetir.motorDegTarihi)
            }

            //-------------------------------------------------------------
            if (motorBilgiGetir.motorGucKW == 0.0){
                tvMotorEtiketGucKw.text = bilgiYok
            }else{
                tvMotorEtiketGucKw.text = ("${motorBilgiGetir.motorGucKW} KW")
            }

            //---------------------------------------------------------
            if (motorBilgiGetir.motorGucHP == 0.0){
                tvMotorEtiketGucHp.text = bilgiYok
            }else {
                tvMotorEtiketGucHp.text = ("${motorBilgiGetir.motorGucHP} HP")
            }

            //------------------------------------------------
            if(motorBilgiGetir.motorNot.isEmpty()){
                tvMotorNot.text = bilgiYok
            }else{
                tvMotorNot.text = (motorBilgiGetir.motorNot)
            }
        }else{
            Toast.makeText(context?.applicationContext, "Bilgiler Getirilemedi", Toast.LENGTH_LONG).show()
        }
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorSalterEtiket,fragment,"fragment_motor_salter_etiket")
        fragmentTransaction.commit()

    }


}
