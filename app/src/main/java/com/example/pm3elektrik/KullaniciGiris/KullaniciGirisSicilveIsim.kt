package com.example.pm3elektrik.KullaniciGiris

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pm3elektrik.AnaSayfa.AnaSayfa
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId


class KullaniciGirisSicilveIsim : AppCompatActivity() {

    var kullaniciModel = KullaniciModel()
    var kullaniciToken: String? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_giris_sicil_ve_isim)

        val isim = findViewById<EditText>(R.id.etKullaniciKayitIsim)
        val sicilNo = findViewById<EditText>(R.id.etKullaniciKayitIsYeriSicil)
        val kaydet = findViewById<Button>(R.id.btnKullaniciyiKaydet)
        val sifre = findViewById<EditText>(R.id.etKullaniciKayitSifre)
        val sifreTekrar = findViewById<EditText>(R.id.etKullaniciKayitSifreTekrar)
        val girisBilgisi = findViewById<Button>(R.id.btnKullaniciGirisSayfasi)
        val sifremiUnuttum = findViewById<TextView>(R.id.tvKullaniciSifremiUnuttum)

        girisBilgisi.setOnClickListener {
            girisBilgisiSayfasi()
        }

        sifremiUnuttum.setOnClickListener {
            sifremiUnuttumSayfasi()
        }

        kaydet.setOnClickListener {

            if (isim.text.toString().isNotEmpty() && sicilNo.text.toString().isNotEmpty() && sifre.text.toString().isNotEmpty() &&
                    sifreTekrar.text.toString().isNotEmpty()) {

                if (sifre.text.toString().equals(sifreTekrar.text.toString())) {
                val sharedPreferences = getSharedPreferences("gelenKullaniciIsmi", 0)
                val editor = sharedPreferences.edit()
                editor.putString("KEY_ISIM", isim.text.toString().toUpperCase())
                editor.putInt("KEY_SICIL_NO", sicilNo.text.toString().toInt())
                editor.putString("KEY_SIFRE", sifre.text.toString())
                editor.apply()

                kullaniciModel.isim = isim.text.toString().toUpperCase()
                kullaniciModel.sicilNo = sicilNo.text.toString().toInt()
                kullaniciModel.sifre = sifre.text.toString()

                try {
                    kullaniciModel.kullaniciToken = kullaniciTokenIDKaydetGuncelle()!!
                } catch (Hata: Exception) {
                }

                val internetConnection = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = internetConnection.activeNetwork

                if (activeNetwork != null) {
                    FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                        .child("Kullanicilar")
                        .child(sicilNo.text.toString())
                        .setValue(kullaniciModel).addOnCompleteListener {

                            if (it.isSuccessful) {
                                Toast.makeText(this, "Kayıt Yapıldı", Toast.LENGTH_SHORT).show()
                                kullaniciKaydiniKontrolEt()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Kayıt Başarısız: ${it.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Toast.makeText(
                                    this, "İnternet Bağlantınızı Kontrol Ediniz", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "İlk Kayıt İçin İnternet Gerekli", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                    Toast.makeText(this, "Şifre ve Şifre Tekrarı Uyuşmuyor!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Boş Alanları Doldurunuz", Toast.LENGTH_LONG).show()
            }
        }

        //kullaniciKaydiniKontrolEt()

        kullaniciTokenIDKaydetGuncelle()

    }



    private fun kullaniciTokenIDKaydetGuncelle(): String? {

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (it.isSuccessful) {
                kullaniciToken = it.result?.token

                val sharedPreferences = getSharedPreferences("gelenKullaniciIsmi", 0)
                val editor = sharedPreferences.edit()
                editor.putString("KEY_TOKEN", it.result?.token.toString())
                editor.apply()


                Log.e("token kaydi","oluşturuldu $kullaniciToken")
            }
            else {

                val sharedPreferences = getSharedPreferences("gelenKullaniciIsmi", 0)
                val tokenKaydi = sharedPreferences.getString("KEY_TOKEN", "") as String
                if(tokenKaydi.isNotEmpty()){
                    Log.e("token kaydi","bulundu $tokenKaydi")
                }
                Log.e("token kaydi","bulunamadı")
            }

        }
        Log.e("KullaniciToken", "$kullaniciToken")
        return kullaniciToken
    }

    private fun kullaniciKaydiniKontrolEt() {

        val sharedPreferences = getSharedPreferences("gelenKullaniciIsmi", 0)
        val isim = sharedPreferences.getString("KEY_ISIM", "")
        val sicilNo = sharedPreferences.getInt("KEY_SICIL_NO", 0)

        if (isim!!.isNotEmpty() && sicilNo != 0) {

            Log.e("girisKayitKontrol", "$isim $sicilNo")

            FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar")
                .orderByValue()
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {

                        for (okunan in p0.children) {

                            try {
                                val gelen = okunan.getValue(KullaniciModel::class.java)

                                if (gelen?.sicilNo != null) {
                                    if (gelen.sicilNo == sicilNo) {

                                        tokenIDGuncelle(sicilNo)

                                        val gelenIntent = intent

                                        val gelenTur = gelenIntent.getStringExtra("gelenTur")
                                        val gelenTag = gelenIntent.getStringExtra("gelenTag")


                                        if (gelenTur != null) {

                                            val pendingIntent = Intent(
                                                this@KullaniciGirisSicilveIsim,
                                                AnaSayfa::class.java
                                            )
                                            pendingIntent.flags =
                                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            pendingIntent.putExtra("gelenTur", gelenTur)
                                            pendingIntent.putExtra("gelenTag", gelenTag)
                                            startActivity(pendingIntent)

                                        } else {

                                            val intent = Intent(
                                                this@KullaniciGirisSicilveIsim,
                                                AnaSayfa::class.java
                                            )
                                            intent.flags =
                                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            startActivity(intent)
                                            overridePendingTransition(
                                                R.anim.fab_slide_in_from_left,
                                                R.anim.fab_slide_out_to_left
                                            )
                                        }
                                    }
                                } else {
                                    Toast.makeText(
                                        this@KullaniciGirisSicilveIsim,
                                        "Lütfen Kayıt Olunuz",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }catch (hata : Exception){}
                        }
                    }
                })
        }
    }

    private fun tokenIDGuncelle(sicilNo: Int) {

        Log.e("sicil No","$sicilNo")
        Log.e("gelen Token","${kullaniciTokenIDKaydetGuncelle()}")
        FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar")
            .orderByValue()
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {

                    for (okunan in p0.children) {

                        try {
                            val gelen = okunan.getValue(KullaniciModel::class.java)
                            if (gelen?.sicilNo == sicilNo) {


                                Log.e("gelen Sicil", "${gelen.sicilNo}")
                                FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                                    .child("Kullanicilar")
                                    .child(gelen.sicilNo.toString())
                                    .child("kullaniciToken")
                                    .setValue(kullaniciTokenIDKaydetGuncelle())

                            }

                        }catch (hata : Exception){
                        }
                    }
                }
            })
    }

    private fun girisBilgisiSayfasi() {

        val kullaniciGirisSayfasi = KullaniciGirisDialogFragment()
        kullaniciGirisSayfasi.show(this.supportFragmentManager,"kullanici_giris_sayfasi_dialog_fr")

    }

    private fun sifremiUnuttumSayfasi() {

        val sifremiUnuttumSayfasi = SifremiUnuttumDialogFragment()
        sifremiUnuttumSayfasi.show(this.supportFragmentManager,"sifremi_unuttum_dialog")
    }
}

