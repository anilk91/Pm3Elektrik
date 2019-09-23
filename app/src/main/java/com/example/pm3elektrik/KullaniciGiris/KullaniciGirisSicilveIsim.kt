package com.example.pm3elektrik.KullaniciGiris

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pm3elektrik.AnaSayfa.AnaSayfa
import com.example.pm3elektrik.FirebaseCloudMessage.FirebaseCloudMessage
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class KullaniciGirisSicilveIsim : AppCompatActivity() {

    var kullaniciModel = KullaniciModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_giris_sicil_ve_isim)

        val isim = findViewById<EditText>(R.id.etKullaniciKayitIsim)
        val sicilNo = findViewById<EditText>(R.id.etKullaniciKayitIsYeriSicil)
        val ekle = findViewById<Button>(R.id.btnKullaniciyiKaydet)

        kullaniciKaydiniKontrolEt()

        kullaniciTokenIDKaydet()

        ekle.setOnClickListener {

            if (isim.text.toString().isNotEmpty() && sicilNo.text.toString().isNotEmpty()){

                val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("KEY_ISIM",isim.text.toString().toUpperCase())
                editor.putInt("KEY_SICIL_NO",sicilNo.text.toString().toInt())
                editor.apply()

                kullaniciModel.isim = isim.text.toString().toUpperCase()
                kullaniciModel.sicilNo = sicilNo.text.toString().toInt()

                FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar").child(sicilNo.text.toString())
                    .setValue(kullaniciModel).addOnCompleteListener {

                        if (it.isComplete){
                            Toast.makeText(this,"Kayıt Yapıldı", Toast.LENGTH_SHORT).show()
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

    private fun kullaniciTokenIDKaydet() {


    }


    private fun kullaniciKaydiniKontrolEt() {

        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val isim = sharedPreferences.getString("KEY_ISIM","")
        val sicilNo = sharedPreferences.getInt("KEY_SICIL_NO",0)

        if (isim!!.isNotEmpty() && sicilNo != 0){

            FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar").orderByValue()
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {

                        for (okunan in p0.children){

                            val gelen = okunan.getValue(KullaniciModel::class.java)

                            if (gelen!!.sicilNo.equals(sicilNo)){

                                val intent = Intent(this@KullaniciGirisSicilveIsim , AnaSayfa::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this@KullaniciGirisSicilveIsim,"Lütfen Kayıt Olunuz",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
        }else{Toast.makeText(this,"Kayıtlı Kullanıcı Yok",Toast.LENGTH_LONG).show()}
    }
}
