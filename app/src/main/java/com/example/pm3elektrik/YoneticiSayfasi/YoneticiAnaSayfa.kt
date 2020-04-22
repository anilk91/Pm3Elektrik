package com.example.pm3elektrik.YoneticiSayfasi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pm3elektrik.KullaniciGiris.KullaniciGirisSicilveIsim
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_ana_sayfa.*
import kotlinx.android.synthetic.main.activity_yonetici_ana_sayfa.*

class YoneticiAnaSayfa : AppCompatActivity() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar")
    val kullaniciListeDizisi = ArrayList<KullaniciModel>()
    lateinit var myAdapter : RVKullaniciListe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yonetici_ana_sayfa)

        gelenKullaniciBilgileriniOku(this)

        btnYoneticiKayitSayfasinaGec.setOnClickListener {
            val intent = Intent(this,KullaniciGirisSicilveIsim::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun gelenKullaniciBilgileriniOku(mContext: Context) {
        ref.orderByValue().addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {

                for (okunan in p0.children){
                    val gelen = okunan.getValue(KullaniciModel::class.java)
                    if (gelen != null){
                        kullaniciListeDizisi.add(KullaniciModel(gelen.isim, gelen.sicilNo, gelen.sifre, gelen.kullaniciToken, gelen.motorYetki, gelen.cekmeceYetki, gelen.driveUniteYetki, gelen.telefonYetki, gelen.ambarKayitYetki))
                    }
                    recyclerAdapter(kullaniciListeDizisi, mContext)
                }


            }


        })
    }

    private fun recyclerAdapter(kullaniciListeDizisi: ArrayList<KullaniciModel>, mContext: Context) {

        myAdapter = RVKullaniciListe(kullaniciListeDizisi,mContext)
        rvKullaniciListe?.adapter = myAdapter

        val mLayoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL,false)
        rvKullaniciListe?.layoutManager = mLayoutManager
    }
}
