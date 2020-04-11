package com.example.pm3elektrik

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import com.example.pm3elektrik.AnaSayfa.AnaSayfa
import com.example.pm3elektrik.KullaniciGiris.KullaniciGirisSicilveIsim
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.zip.CheckedOutputStream
import kotlin.math.min
import kotlin.time.minutes
import kotlin.time.seconds

class MainActivity : AppCompatActivity() {

    var gelenSicilNo = 0
    var gelenIsim = ""
    val gelenYetkiler = ArrayList<KullaniciModel>()


    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kullaniciBilgileriniOku()
        kullaniciKaydiKontrolEt()

    }

    private fun kullaniciKaydiKontrolEt() {
        ref.orderByKey()
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    for (dataGetir in p0.children) {

                        val gelen = dataGetir.getValue(KullaniciModel::class.java) as KullaniciModel
                        if (gelen.sicilNo == gelenSicilNo) {
                            gelenYetkiler.add(
                                KullaniciModel(
                                    gelen.isim,
                                    gelen.sicilNo,
                                    gelen.kullaniciToken,
                                    gelen.motorYetki,
                                    gelen.cekmeceYetki,
                                    gelen.driveUniteYetki,
                                    gelen.telefonYetki,
                                    gelen.ambarKayitYetki
                                )
                            )
                        }
                    }
                    gelenBilgileriKaydet(gelenYetkiler)
                }


            })
    }

    private fun gelenBilgileriKaydet(gelenYetkiler: ArrayList<KullaniciModel>) {
        if (gelenYetkiler.isEmpty()) {

            makeText(this,"Kayıt Bulunamadı Kayıt Sayfasına Yönlendiriliyorsunuz...",Toast.LENGTH_SHORT).show()
            object : CountDownTimer(3000,100){
                override fun onTick(p0: Long) {}
                override fun onFinish() {
                    kayitSayfasinaGec()
                }
            }.start()

        } else {
            val isim = gelenYetkiler.get(0).isim
            val sicilNo = gelenYetkiler.get(0).sicilNo.toString()
            val kullaniciToken = gelenYetkiler.get(0).kullaniciToken
            val ambarYetki = gelenYetkiler.get(0).ambarKayitYetki
            val telefonYetki = gelenYetkiler.get(0).telefonYetki
            val motorEkleSilDuzenleYetki = gelenYetkiler.get(0).motorYetki
            val driveUniteYetki = gelenYetkiler.get(0).driveUniteYetki
            val cekmeceEkleSilDuzenleYetki = gelenYetkiler.get(0).cekmeceYetki

            val sharedPreferences = getSharedPreferences("gelenKullaniciBilgileri", 0)
            val editor = sharedPreferences.edit()
            editor.putString("KEY_GELEN_ISIM", isim)
            editor.putString("KEY_GELEN_SICIL_NO", sicilNo)
            editor.putString("KEY_KULLANICI_TOKEN", kullaniciToken)
            editor.putString("KEY_AMBAR_YETKI", ambarYetki)
            editor.putString("KEY_TELEFON_YETKI", telefonYetki)
            editor.putString("KEY_MOTOR_YETKI", motorEkleSilDuzenleYetki)
            editor.putString("KEY_DRİVE_UNİTE_YETKI", driveUniteYetki)
            editor.putString("KEY_CEKMECE_YETKI", cekmeceEkleSilDuzenleYetki)
            editor.apply()

            makeText(this, "Hoş Geldin $isim ", Toast.LENGTH_SHORT).show()

            object : CountDownTimer(3000,100){
                override fun onTick(p0: Long) {}
                override fun onFinish() {
                    anaSayfayaGec()
                }
            }.start()


        }
}

    private fun kullaniciBilgileriniOku(){
        val sharedPreferences = getSharedPreferences("gelenKullaniciIsmi", 0)
        gelenIsim = sharedPreferences.getString("KEY_ISIM", "").toString()
        gelenSicilNo = sharedPreferences.getInt("KEY_SICIL_NO", 0)


    }

    private fun anaSayfayaGec(){
        val intent = Intent(this,AnaSayfa::class.java)
        startActivity(intent)
    }

    private fun kayitSayfasinaGec(){
        val intent = Intent(this,KullaniciGirisSicilveIsim::class.java)
        startActivity(intent)
    }
}
