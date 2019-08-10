package com.example.pm3elektrik.MotorListeSayfasi.MotorveSalterEtiketleri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.MotorEkle
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

class MotorSalterEtiketi : AppCompatActivity() {


    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")


    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motor_salter_etiketi)



        val motorSalterEtiketClose = findViewById<ImageView>(R.id.imgMotorSalterClose)
        val motorEdit = findViewById<ImageView>(R.id.imgEditMotorBilgi)
        val salterSurucuEdit = findViewById<ImageView>(R.id.imgEditSalterSurucu)

        motorSalterEtiketClose.setOnClickListener {

            onDestroy()
        }
        motorEdit.setOnClickListener {
            changeFragment(MotorEkle())

        }
        salterSurucuEdit.setOnClickListener {
            changeFragment(SalterEkle())
        }

        firebaseDBOku()

    }

    private fun firebaseDBOku() {

        val gelenIntent = intent
        val gelenMotorTag = gelenIntent.getStringExtra("motor_tag")

        //-----------Motor Etiket Bilgilerini Getir--------------
        ref.child("Motor")
            .child(gelenMotorTag)
            .addListenerForSingleValueEvent( object  : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    motorEtiketBilgileriGelen(p0)
                }
            })

        //-----------Şalter Bilgilerini Getir--------------
        ref.child("Salter")
            .child(gelenMotorTag)
            .addListenerForSingleValueEvent( object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                salterEtiketBilgileriGelen(p0)
            }
        })


        //-----------Sürücü Bilgilerini Getir--------------
        ref.child("Surucu")
            .child(gelenMotorTag)
            .addListenerForSingleValueEvent( object  : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    surucuEtiketBilgileriGelen(p0)

                }
            })

    }

    private fun surucuEtiketBilgileriGelen(p0: DataSnapshot) {

        val surucuBilgileriGetir =p0.getValue(SurucuModel::class.java)

        tvSurucuEtiketTipi.setText("${surucuBilgileriGetir?.surucuIsim}")
        tvSurucuEtiketModel.setText("${surucuBilgileriGetir?.surucuModel}")
        tvSurucuEtiketKonBoyut.setText("${surucuBilgileriGetir?.surucuBoyut}")
        tvSurucuEtiketKonDip.setText("${surucuBilgileriGetir?.surucuDIPSivic}")
        tvSurucuEtiketDegTarihi.setText("${surucuBilgileriGetir?.surucuDegTarihi}")

    }

    private fun salterEtiketBilgileriGelen(p0: DataSnapshot) {

        val cekmeceBilgiGetir = p0.getValue(SalterModel::class.java)

        tvSalterMarka.setText("${cekmeceBilgiGetir?.salterMarka}")
        tvSalterKapasite.setText("${cekmeceBilgiGetir?.salterKapasite}")
        tvSalterEtiketCat.setText("${cekmeceBilgiGetir?.salterCAT}")
        tvSalterEtiketStyle.setText("${cekmeceBilgiGetir?.salterSTYLE}")
        tvSalterEtiketDemeraj.setText("${cekmeceBilgiGetir?.salterDemeraj}")
        tvSalterEtiketDegTarihi.setText("${cekmeceBilgiGetir?.salterDegTarihi}")



    }

    private fun motorEtiketBilgileriGelen(p0: DataSnapshot) {


        val motorBilgiGetir = p0.getValue(MotorModel::class.java)
        tvMotorEtiketPompaIsim.setText("${motorBilgiGetir?.motorIsim}")
        tvMotorEtiketTag.setText("${motorBilgiGetir?.motorTag}")
        tvMotorEtiketGucKw.setText("${motorBilgiGetir?.motorGucKW} KW")
        tvMotorEtiketGucHp.setText("${motorBilgiGetir?.motorGucHP} HP")
        tvMotorEtiketDevir.setText("${motorBilgiGetir?.motorDevir} d/d")
        tvMotorEtiketNomTripAkim.setText("${motorBilgiGetir?.motorNomTripAkimi} A")
        tvMotorEtiketInsaTipi.setText("${motorBilgiGetir?.motorInsaTipi}")
        tvMotorEtiketFlans.setText("${motorBilgiGetir?.motorFlans}")
        tvMotorEtiketAdres.setText("${motorBilgiGetir?.motorAdres}")
        tvMotorEtiketMccYeri.setText("${motorBilgiGetir?.motorMCCYeri}")
        tvMotorEtiketDegTarih.setText("${motorBilgiGetir?.motorDegTarihi}")
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = this@MotorSalterEtiketi.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorSalterEtiket,fragment,"motor_salter_etiket_fr")
        fragmentTransaction.commit()

    }
}
