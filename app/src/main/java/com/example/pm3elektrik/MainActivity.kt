package com.example.pm3elektrik

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.makeText
import com.example.pm3elektrik.AnaSayfa.AnaSayfa
import com.example.pm3elektrik.KullaniciGiris.KullaniciGirisSicilveIsim
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.YoneticiSayfasi.YoneticiAnaSayfa
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        var gelenSicilNo = 0
        var gelenIsim = ""
    }

    var gelenYetkiler = ArrayList<KullaniciModel>()


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

                        val sharedPreferences = getSharedPreferences("gelenKullaniciBilgileri", 0)
                        gelenIsim = sharedPreferences.getString("KEY_GELEN_ISIM", "").toString()
                        gelenSicilNo = sharedPreferences.getInt("KEY_GELEN_SICIL_NO", 0)

                        if (gelenYetkiler.size >= 2){
                            gelenYetkiler.clear()
                        }

                        if (gelen.sicilNo == gelenSicilNo) {
                            gelenYetkiler.add(
                                KullaniciModel(
                                    gelen.isim,
                                    gelen.sicilNo,
                                    gelen.sifre,
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

        if (gelenSicilNo == 1111){
            yoneticiSayfasinaGec()
        }else{
        if (gelenYetkiler.isEmpty()) {

            makeText(this,"Kayıt Bulunamadı Kayıt Sayfasına Yönlendiriliyorsunuz...",Toast.LENGTH_SHORT).show()
            object : CountDownTimer(2000,100){
                override fun onTick(p0: Long) {}
                override fun onFinish() {
                    kayitSayfasinaGec()
                }
            }.start()

        } else {
            val isim = gelenYetkiler.get(gelenYetkiler.size-1).isim
            val sicilNo = gelenYetkiler.get(gelenYetkiler.size-1).sicilNo
            val kullaniciToken = gelenYetkiler.get(gelenYetkiler.size-1).kullaniciToken
            val ambarYetki = gelenYetkiler.get(gelenYetkiler.size-1).ambarKayitYetki
            val telefonYetki = gelenYetkiler.get(gelenYetkiler.size-1).telefonYetki
            val motorEkleSilDuzenleYetki = gelenYetkiler.get(gelenYetkiler.size-1).motorYetki
            val driveUniteYetki = gelenYetkiler.get(gelenYetkiler.size-1).driveUniteYetki
            val cekmeceEkleSilDuzenleYetki = gelenYetkiler.get(gelenYetkiler.size-1).cekmeceYetki
            val gelenSifre = gelenYetkiler.get(gelenYetkiler.size-1).sifre

            val sharedPreferences = getSharedPreferences("gelenKullaniciBilgileri", 0)
            val editor = sharedPreferences.edit()
            editor.putString("KEY_GELEN_ISIM", isim)
            editor.putInt("KEY_GELEN_SICIL_NO", sicilNo)
            editor.putString("KEY_KULLANICI_TOKEN", kullaniciToken)
            editor.putString("KEY_AMBAR_YETKI", ambarYetki)
            editor.putString("KEY_TELEFON_YETKI", telefonYetki)
            editor.putString("KEY_MOTOR_YETKI", motorEkleSilDuzenleYetki)
            editor.putString("KEY_DRİVE_UNİTE_YETKI", driveUniteYetki)
            editor.putString("KEY_CEKMECE_YETKI", cekmeceEkleSilDuzenleYetki)
            editor.putString("KEY_GELEN_SIFRE", gelenSifre)
            editor.apply()

            hosGeldinYazisiniGoster(isim)

            object : CountDownTimer(2500, 100) {
                override fun onTick(p0: Long) {}
                override fun onFinish() {
                    anaSayfayaGec()
                }
            }.start()
        }
        }
}

    private fun hosGeldinYazisiniGoster(isim: String) {
        tvHosgeldin.visibility = View.VISIBLE
        tvHosGeldinGelenKisi.visibility = View.VISIBLE
        tvHosGeldinGelenKisi.setText(isim)
    }

    private fun kullaniciBilgileriniOku(){
        val sharedPreferences = getSharedPreferences("gelenKullaniciBilgileri", 0)
        gelenIsim = sharedPreferences.getString("KEY_GELEN_ISIM", "").toString()
        gelenSicilNo = sharedPreferences.getInt("KEY_GELEN_SICIL_NO", 0)


    }

    private fun anaSayfayaGec(){
        val intent = Intent(this,AnaSayfa::class.java)
        startActivity(intent)
        finish()
    }

    private fun kayitSayfasinaGec(){
        val intent = Intent(this,KullaniciGirisSicilveIsim::class.java)
        startActivity(intent)
        finish()
    }

    private fun yoneticiSayfasinaGec(){
        val intent = Intent(this,YoneticiAnaSayfa::class.java)
        startActivity(intent)
        finish()

    }
}
