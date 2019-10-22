package com.example.pm3elektrik.MotorListeSayfasi.MotorveSalterEtiketleri


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

        Log.e("motorEtiketGor","$motorTag")

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
            val transaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
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

                if (surucuBilgileriGetir.surucuDegTarihi.isNullOrBlank()){
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

                if (surucuBilgileriGetir.surucuDegTarihi.isNullOrBlank()){

                    tvSurucuEtiketDegTarihi.text = bilgiYok
                }else{
                    tvSurucuEtiketDegTarihi.text = surucuBilgileriGetir.surucuDegTarihi
                }


            }
        }
    }

    private fun salterEtiketBilgileriGelen(p0: DataSnapshot) {

        val cekmeceBilgileri = p0.getValue(SalterModel::class.java)

        //-----------------------------
        if (cekmeceBilgileri?.salterMarka.isNullOrBlank()) {
            tvSalterMarka.text = (bilgiYok)
        } else  {
            tvSalterMarka.text = (cekmeceBilgileri?.salterMarka)
        }

        //--------------------------------
        if (cekmeceBilgileri?.salterKapasite.isNullOrBlank()) {
            tvSalterKapasite.text = (bilgiYok)
        } else  {
            tvSalterKapasite.text = (cekmeceBilgileri?.salterKapasite)
        }

        //--------------------------------
        if (cekmeceBilgileri?.salterCAT.isNullOrBlank()) {
            tvSalterEtiketCat.text = (bilgiYok)
        } else {
            tvSalterEtiketCat.text = (cekmeceBilgileri?.salterCAT)
        }
        //------------------------------
         if (cekmeceBilgileri?.salterSTYLE.isNullOrBlank()) {
            tvSalterEtiketStyle.text = (bilgiYok)
        } else  {
            tvSalterEtiketStyle.text = (cekmeceBilgileri?.salterSTYLE)
        }
        //-------------------------------------
        if (cekmeceBilgileri?.salterDemeraj.isNullOrBlank()) {
            tvSalterEtiketDemeraj.text = (bilgiYok)
        } else  {
            tvSalterEtiketDemeraj.text = (cekmeceBilgileri?.salterDemeraj)
        }
        //-----------------------------------
        if (cekmeceBilgileri?.salterDegTarihi.isNullOrBlank()) {
            tvSalterEtiketDegTarihi.text = (bilgiYok)
        } else  {
            tvSalterEtiketDegTarihi.text = (cekmeceBilgileri?.salterDegTarihi)
        }
        //-----------------------------------
        if (cekmeceBilgileri?.cekmeceDegTarihi.isNullOrBlank()) {
            tvCekmeceDegTarihi.text = (bilgiYok)
        } else  {
            tvCekmeceDegTarihi.text = (cekmeceBilgileri?.cekmeceDegTarihi)
        }

    }

    private fun motorEtiketBilgileriGelen(p0: DataSnapshot) {


        val motorBilgiGetir = p0.getValue(MotorModel::class.java)

        if (motorBilgiGetir != null) {

            if (!motorBilgiGetir.motorTag.isNullOrBlank()){
                tvMotorEtiketTag.text = (motorBilgiGetir.motorTag)
            }
            if (!motorBilgiGetir.motorMCCYeri.isNullOrBlank()){
                tvMotorEtiketMccYeri.text = (motorBilgiGetir.motorMCCYeri)
            }

            //------------------------------------------------------------
            if (motorBilgiGetir.motorIsim.isNullOrBlank()){
                tvMotorEtiketPompaIsim.text = bilgiYok
            }else{
                tvMotorEtiketPompaIsim.text = (motorBilgiGetir.motorIsim)
            }

            //------------------------------------------------------------
            if (motorBilgiGetir.motorDevir.isNullOrBlank()){
                tvMotorEtiketDevir.text = bilgiYok
            }else{
                tvMotorEtiketDevir.text = (motorBilgiGetir.motorDevir + " D/d")
            }

            //------------------------------------------------------------
            if(motorBilgiGetir.motorNomTripAkimi.isNullOrBlank()){
                tvMotorEtiketNomTripAkim.text = bilgiYok
            }else{
                tvMotorEtiketNomTripAkim.text = (motorBilgiGetir.motorNomTripAkimi + " A")
            }

            //--------------------------------------------------------------
            if (motorBilgiGetir.motorInsaTipi.isNullOrBlank()){
                tvMotorEtiketInsaTipi.text = bilgiYok
            }else{
                tvMotorEtiketInsaTipi.text = (motorBilgiGetir.motorInsaTipi)
            }

            //----------------------------------------------------------------

            if (motorBilgiGetir.motorFlans.isNullOrBlank()){
                tvMotorEtiketFlans.text = bilgiYok
            }else{
                tvMotorEtiketFlans.text = (motorBilgiGetir.motorFlans)
            }

            //-----------------------------------------------------------------
            if (motorBilgiGetir.motorAdres.isNullOrBlank()){
                tvMotorEtiketAdres.text = bilgiYok
            }else{
                tvMotorEtiketAdres.text = (motorBilgiGetir.motorAdres)
            }

            //---------------------------------------------------------------
            if (motorBilgiGetir.motorDegTarihi.isNullOrBlank()){
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
            if(motorBilgiGetir.motorNot.isNullOrBlank()){
                tvMotorNot.text = bilgiYok
            }else{
                tvMotorNot.text = ("${motorBilgiGetir.motorNot}")
            }
        }
    }
    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorSalterEtiket,fragment,"fragment_motor_salter_etiket")
        fragmentTransaction.commit()

    }


}
