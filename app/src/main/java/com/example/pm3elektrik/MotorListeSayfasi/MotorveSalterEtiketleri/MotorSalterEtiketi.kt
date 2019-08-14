package com.example.pm3elektrik.MotorListeSayfasi.MotorveSalterEtiketleri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.DuzenleFragmentleri.MotorEtiketDuzenle
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.SalterEkle
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.MotorModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SalterModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SurucuModel
import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_motor_salter_etiketi.*
import kotlinx.android.synthetic.main.activity_motor_salter_etiketi.tvMotorEtiketAdres
import kotlinx.android.synthetic.main.activity_motor_salter_etiketi.tvMotorEtiketDevir
import kotlinx.android.synthetic.main.activity_motor_salter_etiketi.tvMotorEtiketFlans
import kotlinx.android.synthetic.main.activity_motor_salter_etiketi.tvMotorEtiketGucHp
import kotlinx.android.synthetic.main.activity_motor_salter_etiketi.tvMotorEtiketGucKw
import kotlinx.android.synthetic.main.activity_motor_salter_etiketi.tvMotorEtiketInsaTipi
import kotlinx.android.synthetic.main.activity_motor_salter_etiketi.tvMotorEtiketTag
import java.text.DecimalFormat

class MotorSalterEtiketi : AppCompatActivity() {


    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motor_salter_etiketi)


        val motorSalterEtiketClose = findViewById<ImageView>(R.id.imgMotorSalterClose)
        val motorEdit = findViewById<ImageView>(R.id.imgEditMotorBilgi)
        val salterSurucuEdit = findViewById<ImageView>(R.id.imgEditSalterSurucu)

        motorSalterEtiketClose.setOnClickListener {

            onBackPressed()
        }
        motorEdit.setOnClickListener {

            val gelenIntent = intent
            val gelenMotorTag = gelenIntent.getStringExtra("motor_tag")

            Log.e("activity_gelen",gelenMotorTag)
            val bundle = Bundle()
            bundle.putString("motor_tag",gelenMotorTag)

            val frMotorDuzenle = MotorEtiketDuzenle()
            frMotorDuzenle.arguments = bundle
            val fragmentTransaction: FragmentTransaction = this@MotorSalterEtiketi.supportFragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.containerMotorSalterEtiket, frMotorDuzenle, "motor_salter_etiket_fr")
            fragmentTransaction.commit()

        }
        salterSurucuEdit.setOnClickListener {
            changeFragment(SalterEkle())
        }

        firebaseDBOku()

    }

    private fun firebaseDBOku() {

        val gelenIntent = intent
        val gelenMotorTag = gelenIntent.getStringExtra("motor_tag")

        //-----------Şalter Etiket Bilgilerini Getir--------------
        ref.child("Salter")
            .child(gelenMotorTag)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    salterEtiketBilgileriGelen(p0)
                }
            })

        //-----------Motor Bilgilerini Getir--------------
        ref.child("Motor")
            .child(gelenMotorTag)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    motorEtiketBilgileriGelen(p0)
                }
            })


        //-----------Sürücü Bilgilerini Getir--------------
        ref.child("Surucu")
            .child(gelenMotorTag)
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
            val gucKw = DecimalFormat("##.##").format(motorBilgiGetir.motorGucKW.toDouble())
            val gucHp = DecimalFormat("##.##").format(motorBilgiGetir.motorGucHP.toDouble())

            tvMotorEtiketPompaIsim.text = (motorBilgiGetir.motorIsim)
            tvMotorEtiketTag.text = (motorBilgiGetir.motorTag)
            tvMotorEtiketGucKw.text = ("$gucKw KW")
            tvMotorEtiketGucHp.text = ("$gucHp HP")
            tvMotorEtiketDevir.text = (motorBilgiGetir.motorDevir + " d/d")
            tvMotorEtiketNomTripAkim.text = (motorBilgiGetir.motorNomTripAkimi + " A")
            tvMotorEtiketInsaTipi.text = (motorBilgiGetir.motorInsaTipi)
            tvMotorEtiketFlans.text = (motorBilgiGetir.motorFlans)
            tvMotorEtiketAdres.text = (motorBilgiGetir.motorAdres)
            tvMotorEtiketMccYeri.text = (motorBilgiGetir.motorMCCYeri)
            tvMotorEtiketDegTarih.text = (motorBilgiGetir.motorDegTarihi)
        } else {
        }

    }

    private fun changeFragment(fragment: Fragment) {



    }
}
