uspackage com.example.pm3elektrik.AyarlarSayfasi


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.AnaSayfa.AnaSayfa
import com.example.pm3elektrik.KullaniciGiris.KullaniciGirisSicilveIsim
import com.example.pm3elektrik.KullaniciGiris.KullaniciKayitModel.KullaniciModel
import com.example.pm3elektrik.KullaniciGiris.SifremiUnuttumDialogFragment
import com.example.pm3elektrik.MainActivity

import com.example.pm3elektrik.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Ayarlar : Fragment() {

    var gelenIsim : String? = ""
    var gelenSicilNo  = 0
    var gelenKullaniciToken : String? = ""
    var gelenSifre : String? = ""
    var motorEkleYetki : String? = "yok"
    var cekmeceEkleYetki : String? = "yok"
    var driveEkleYetki : String? = "yok"
    var ambarEkleYetki : String? = "yok"
    var telefonEkleYetki : String? = "yok"

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("Kullanicilar")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ayarlar, container, false)

        kullaniciBilgileriniOku()

        val isim = view.findViewById<TextView>(R.id.tvGelenKullaniciIsmi)
        val sicilNo = view.findViewById<TextView>(R.id.tvGelenKullaniciSicilNo)
        val btnKayitSayfasi = view.findViewById<Button>(R.id.btnKayitSayfasinaGec)
        val sifremiUnuttum = view.findViewById<TextView>(R.id.tvAyarlarSifremiUnuttum)
        val sifreGuncelleButonu = view.findViewById<Button>(R.id.btnAyarlarSifreGuncellemeyiAc)
        val sifreCardView = view.findViewById<CardView>(R.id.cardViewSifreIslemleri)
        val sifreGuncelle = view.findViewById<Button>(R.id.btnAyarlarSifreGuncelle)
        val motorEklemeSilme = view.findViewById<CheckBox>(R.id.checkBoxMotorEklemeSilme)
        val cekmeceEklemeSilme = view.findViewById<CheckBox>(R.id.checkBoxCekmeceEklemeSilme)
        val driveEklemeSilme = view.findViewById<CheckBox>(R.id.checkBoxDriveUniteMotor)
        val ambarEklemeSilme = view.findViewById<CheckBox>(R.id.checkBoxAmbarKayit)
        val telefonEklemeSilme = view.findViewById<CheckBox>(R.id.checkBoxTelefon)
        val mevcutSifre = view.findViewById<EditText>(R.id.etAyarlarMevcutSifre)
        val yeniSifre = view.findViewById<EditText>(R.id.etAyarlarYeniSifre)
        val yeniSifreTekrar  = view.findViewById<EditText>(R.id.etAyarlarYeniSifreTekrar)

        bilgileriAlveIsle(isim,sicilNo,motorEklemeSilme, cekmeceEklemeSilme, driveEklemeSilme, ambarEklemeSilme, telefonEklemeSilme)

        sifreGuncelle.setOnClickListener {

            if (mevcutSifre.text.toString().isNotEmpty() && yeniSifre.text.toString().isNotEmpty() && yeniSifreTekrar.text.toString().isNotEmpty()) {
                Log.e("gelenSİcil", "$gelenSicilNo")
                ref.child(gelenSicilNo.toString())
                    .orderByKey().addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            firebaseBilgileriniOku(p0, mevcutSifre, yeniSifre, yeniSifreTekrar)
                        }
                    })
            } else {
                Toast.makeText(context, "Boş Alanları Doldurunuz!", Toast.LENGTH_SHORT).show()
            }

        }

        sifreGuncelleButonu.setOnClickListener {
            if (sifreCardView.visibility == View.VISIBLE){
                sifreCardView.visibility = View.INVISIBLE
            }else{
                sifreCardView.visibility = View.VISIBLE
            }
        }

        btnKayitSayfasi.setOnClickListener {

            val intent = Intent(context!!.applicationContext,KullaniciGirisSicilveIsim::class.java)
            startActivity(intent)
        }

        sifremiUnuttum.setOnClickListener {

            changeFragment()
        }


        return  view
    }

    private fun firebaseBilgileriniOku(p0: DataSnapshot, mevcutSifre: EditText, yeniSifre: EditText, yeniSifreTekrar: EditText) {

            val gelen = p0.getValue(KullaniciModel::class.java)
            if (gelen != null){
                if (gelen.sicilNo.toString() == gelenSicilNo.toString()) {
                        if (gelen.sifre == mevcutSifre.text.toString()) {
                            if (yeniSifre.text.toString() == yeniSifreTekrar.text.toString()) {
                                if (yeniSifre.text.length >= 6 && yeniSifreTekrar.text.length >= 6) {
                                    FirebaseDatabase.getInstance().reference.child("pm3Elektrik")
                                        .child("Kullanicilar").child(this.gelenSicilNo.toString())
                                        .child("sifre")
                                        .setValue(yeniSifre.text.toString())
                                    sifreDegistiktenSonraKaydet(yeniSifre.text.toString())
                                    Toast.makeText(context, "Şifre Değiştirildi", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(context, "Şifre En Az 6 Karakter Olmalı!", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Şifreler Uyuşmuyor!", Toast.LENGTH_SHORT).show()
                            }
                        } else{
                            Toast.makeText(context, "Mevcut Şifre Yanlış!", Toast.LENGTH_SHORT).show()
                        }
                }
            }



    }

    private fun sifreDegistiktenSonraKaydet(yeniSifre: String) {

        val sharedPreferences = activity?.getSharedPreferences("gelenKullaniciBilgileri", 0)
        val editor = sharedPreferences?.edit()
        editor?.putString("KEY_GELEN_SIFRE", yeniSifre)
        editor?.apply()
        Log.e("yeniSifre","$yeniSifre")
    }

    private fun kullaniciBilgileriniOku(){

        val sharedPreferences = activity?.getSharedPreferences("gelenKullaniciBilgileri", 0)
        gelenIsim = sharedPreferences?.getString("KEY_GELEN_ISIM","")
        gelenSicilNo = sharedPreferences!!.getInt("KEY_GELEN_SICIL_NO",0)
        gelenKullaniciToken = sharedPreferences.getString("KEY_KULLANICI_TOKEN","")
        gelenSifre = sharedPreferences.getString("KEY_GELEN_SIFRE","")
        motorEkleYetki = sharedPreferences.getString("KEY_MOTOR_YETKI","")
        cekmeceEkleYetki = sharedPreferences.getString("KEY_CEKMECE_YETKI","")
        driveEkleYetki = sharedPreferences.getString("KEY_DRİVE_UNİTE_YETKI","")
        ambarEkleYetki = sharedPreferences.getString("KEY_AMBAR_YETKI","")
        telefonEkleYetki = sharedPreferences.getString("KEY_TELEFON_YETKI","")

        Log.e("digerSİfre","$gelenSifre")
    }

    private fun bilgileriAlveIsle(isim : TextView, sicilNo : TextView, motorEklemeSilme: CheckBox, cekmeceEklemeSilme: CheckBox, driveEklemeSilme: CheckBox, ambarEklemeSilme: CheckBox, telefonEklemeSilme: CheckBox) {

        isim.setText(gelenIsim)
        sicilNo.setText(gelenSicilNo.toString())

        if (motorEkleYetki == "var"){
            motorEklemeSilme.isChecked = true
        }
        if (cekmeceEkleYetki == "var"){
            cekmeceEklemeSilme.isChecked = true
        }
        if (driveEkleYetki == "var"){
            driveEklemeSilme.isChecked = true
        }
        if (ambarEkleYetki == "var"){
            ambarEklemeSilme.isChecked = true
        }
        if (telefonEkleYetki == "var"){
            telefonEklemeSilme.isChecked = true
        }
    }

    private fun changeFragment(){

        val sifremiUnuttumSayfasi = SifremiUnuttumDialogFragment()
        sifremiUnuttumSayfasi.show(fragmentManager!!,"sifremi_unuttum_dialog")
    }

}
