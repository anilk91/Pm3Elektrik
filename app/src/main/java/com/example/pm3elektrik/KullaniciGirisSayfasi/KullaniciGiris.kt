package com.example.pm3elektrik.KullaniciGirisSayfasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.AnaSayfa.AnaSayfa
import com.example.pm3elektrik.KullaniciGirisSayfasi.KullaniciKayit.KullaniciKayit
import com.example.pm3elektrik.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_kullanici_giris.*

class KullaniciGiris : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_giris)

        val mailAdres = etKullaniciGirisMailAdres.text
        val sifre = etKullaniciGirisSifre
        val yeniUyelik = tvKullaniciGirisYeniUyelik
        val sifremiUnuttum = tvKullaniciGirisSifremiUnuttum
        val btnGirisYap = btnKullaniciGirisYap

        btnGirisYap.setOnClickListener {

            if (!mailAdres.isNullOrEmpty() && !sifre.toString().isNullOrEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(mailAdres.toString(),sifre.toString()).addOnCompleteListener {

                    if(it.isSuccessful){
                        Toast.makeText(this,"Giriş Yapıldı",Toast.LENGTH_LONG).show()
                        val intent = Intent(this,AnaSayfa::class.java)
                        startActivity(intent)
                    }else {
                        Toast.makeText(this,"Giriş Yapılamadı Hata: ${it.exception}",Toast.LENGTH_LONG).show()
                    }
                }

            }else{
                Toast.makeText(this,"Boş Alanları Doldurunuz",Toast.LENGTH_LONG).show()
            }

        }

        yeniUyelik.setOnClickListener {

            changeFragment(KullaniciKayit())

        }
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction? = supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.containerKullaniciGiris,fragment,"activity_kullanici_giris")
        fragmentTransaction?.commit()
    }

}
