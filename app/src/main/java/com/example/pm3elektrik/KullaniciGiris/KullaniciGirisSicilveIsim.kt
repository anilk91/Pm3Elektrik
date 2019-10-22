package com.example.pm3elektrik.KullaniciGiris

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pm3elektrik.AnaSayfa.AnaSayfa
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciYetkiModel
import com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri.MotorEkle
import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId

class KullaniciGirisSicilveIsim : AppCompatActivity() {

    var kullaniciModel = KullaniciModel()
    var kullaniciToken : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_giris_sicil_ve_isim)

        val isim = findViewById<EditText>(R.id.etKullaniciKayitIsim)
        val sicilNo = findViewById<EditText>(R.id.etKullaniciKayitIsYeriSicil)
        val ekle = findViewById<Button>(R.id.btnKullaniciyiKaydet)

        kullaniciTokenIDKaydetGuncelle()

        kullaniciKaydiniKontrolEt()

        ekle.setOnClickListener {

            if (isim.text.toString().isNotEmpty() && sicilNo.text.toString().isNotEmpty()){

                val sharedPreferences = getSharedPreferences("gelenKullaniciIsmi",0)
                val editor = sharedPreferences.edit()
                editor.putString("KEY_ISIM",isim.text.toString().toUpperCase())
                editor.putInt("KEY_SICIL_NO",sicilNo.text.toString().toInt())
                editor.apply()

                kullaniciModel.isim = isim.text.toString().toUpperCase()
                kullaniciModel.sicilNo = sicilNo.text.toString().toInt()
                kullaniciModel.kullaniciToken = kullaniciTokenIDKaydetGuncelle()!!


                FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar").child(sicilNo.text.toString())
                    .setValue(kullaniciModel).addOnCompleteListener {

                        if (it.isComplete){
                            Toast.makeText(this,"Kayıt Yapıldı", Toast.LENGTH_SHORT).show()
                            kullaniciKaydiniKontrolEt()
                        }else {
                            Toast.makeText(this,"Kayıt Başarısız: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(this,"İnternet Bağlantınızı Kontrol Ediniz", Toast.LENGTH_SHORT).show()
                        }
                    }

            }else {
                Toast.makeText(this,"Boş Alanları Doldurunuz",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun kullaniciTokenIDKaydetGuncelle(): String? {
            FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
                kullaniciToken = it.result?.token
            }

                Log.e("KullaniciToken","$kullaniciToken")
        return kullaniciToken
    }

    private fun kullaniciKaydiniKontrolEt() {

        val sharedPreferences = getSharedPreferences("gelenKullaniciIsmi",0)
        val isim = sharedPreferences.getString("KEY_ISIM","")
        val sicilNo = sharedPreferences.getInt("KEY_SICIL_NO",0)

        if (isim!!.isNotEmpty() && sicilNo != 0){

            Log.e("girisKayitKontrol","$isim $sicilNo")

            FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar").orderByValue()
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {

                        for (okunan in p0.children) {

                            val gelen = okunan.getValue(KullaniciModel::class.java)

                            if (gelen?.sicilNo != null) {
                            if (gelen.sicilNo == sicilNo) {

                                tokenIDGuncelle(sicilNo)

                                val gelenIntent = intent

                                    val gelenTur = gelenIntent.getStringExtra("gelenTur")
                                    val gelenTag = gelenIntent.getStringExtra("gelenTag")


                                    if (gelenTur != null){

                                        val pendingIntent = Intent(this@KullaniciGirisSicilveIsim, AnaSayfa::class.java)
                                        pendingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        pendingIntent.putExtra("gelenTur", gelenTur)
                                        pendingIntent.putExtra("gelenTag", gelenTag)
                                        startActivity(pendingIntent)

                                    } else{

                                    val intent = Intent(this@KullaniciGirisSicilveIsim, AnaSayfa::class.java)
                                    startActivity(intent)
                                    }
                                }
                            }else{
                                Toast.makeText(this@KullaniciGirisSicilveIsim,"Lütfen Kayıt Olunuz",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
        }else{Toast.makeText(this,"Kayıtlı Kullanıcı Yok",Toast.LENGTH_LONG).show()}
    }

    private fun tokenIDGuncelle(sicilNo: Int) {

        FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar").child(sicilNo.toString())
            .child("kullaniciToken")
            .setValue(kullaniciTokenIDKaydetGuncelle())
    }
}
