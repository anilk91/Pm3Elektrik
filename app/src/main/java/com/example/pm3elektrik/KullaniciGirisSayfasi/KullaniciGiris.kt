package com.example.pm3elektrik.KullaniciGirisSayfasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.AnaSayfa.AnaSayfa
import com.example.pm3elektrik.KullaniciGirisSayfasi.KullaniciKayit.KullaniciKayit
import com.example.pm3elektrik.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_kullanici_giris.*

class KullaniciGiris : AppCompatActivity() {

    lateinit var mAuthStateListener : FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_giris)

        initMyAuthStateListener()

        val mailAdres = etKullaniciGirisMailAdres.text
        val sifre = etKullaniciGirisSifre.text
        val yeniUyelik = tvKullaniciGirisYeniUyelik
        val sifremiUnuttum = tvKullaniciGirisSifremiUnuttum
        val btnGirisYap = btnKullaniciGirisYap

        btnGirisYap.setOnClickListener {

            if (mailAdres.isNotEmpty() && sifre.toString().isNotEmpty()){

                FirebaseAuth.getInstance().signInWithEmailAndPassword(mailAdres.toString(),sifre.toString()).addOnCompleteListener {

                    if(it.isSuccessful){

                        Toast.makeText(this@KullaniciGiris,"Giriş Yapıldı",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@KullaniciGiris,AnaSayfa::class.java)
                        startActivity(intent)

                    }else {
                        Toast.makeText(this,"Giriş Yapılamadı Hata: ${it.exception?.message}",Toast.LENGTH_LONG).show()
                    }
                }

            }else{
                Toast.makeText(this,"Boş Alanları Doldurunuz",Toast.LENGTH_LONG).show()
            }

        }

        yeniUyelik.setOnClickListener {

            changeFragment(KullaniciKayit())

        }

        sifremiUnuttum.setOnClickListener {  }
    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction? = supportFragmentManager.beginTransaction()
        fragmentTransaction?.replace(R.id.containerKullaniciGiris,fragment,"activity_kullanici_giris")
        fragmentTransaction?.commit()
    }

    private fun initMyAuthStateListener(){

        mAuthStateListener = object : FirebaseAuth.AuthStateListener{

            override fun onAuthStateChanged(p0: FirebaseAuth) {

                val kullanici = p0.currentUser

                    if (kullanici != null) {

                        if (kullanici.isEmailVerified) {

                            Toast.makeText(this@KullaniciGiris, "Giriş Yapıldı", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@KullaniciGiris, AnaSayfa::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@KullaniciGiris,
                                "Mail Onaylanmamış Lütfen Mail Adresinizi Kontrol Ediniz",
                                Toast.LENGTH_LONG
                            ).show()
                            FirebaseAuth.getInstance().signOut()
                        }
                    }

            }


        }



    }

    override fun onStart() {
        super.onStart()

        FirebaseAuth.getInstance().addAuthStateListener ( mAuthStateListener )
    }

    override fun onStop() {
        super.onStop()

        FirebaseAuth.getInstance().removeAuthStateListener ( mAuthStateListener )
    }

}
