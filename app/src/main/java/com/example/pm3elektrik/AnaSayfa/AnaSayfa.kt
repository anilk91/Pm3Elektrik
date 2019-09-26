package com.example.pm3elektrik.AnaSayfa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.ArizaListeSayfasi.ArizaListe
import com.example.pm3elektrik.AyarlarSayfasi.Ayarlar
import com.example.pm3elektrik.DigerBilgilerSayfasi.DigerBilgiler
import com.example.pm3elektrik.KullanicilarSayfasi.Kullanicilar
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.R
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonListesi
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_ana_sayfa.*

class AnaSayfa : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ana_sayfa)

        imgMotorPassive.visibility = View.INVISIBLE
        gelenPendingIntent()

        imgMotorPassive.setOnClickListener {

            imgMotorPassive.visibility = View.INVISIBLE
            imgPhonePassive.visibility = View.VISIBLE
            imgFaultPassive.visibility = View.VISIBLE
            imgOtherPassive.visibility = View.VISIBLE
            imgUserPassive.visibility = View.VISIBLE
            imgSettingPassive.visibility = View.VISIBLE
            changeFragment(MotorListe())

        }

        imgPhonePassive.setOnClickListener{

            imgPhonePassive.visibility = View.INVISIBLE
            imgMotorPassive.visibility = View.VISIBLE
            imgFaultPassive.visibility = View.VISIBLE
            imgOtherPassive.visibility = View.VISIBLE
            imgUserPassive.visibility = View.VISIBLE
            imgSettingPassive.visibility = View.VISIBLE
            changeFragment(TelefonListesi())
        }

        imgFault.setOnClickListener {

            imgFaultPassive.visibility = View.INVISIBLE
            imgMotorPassive.visibility = View.VISIBLE
            imgOtherPassive.visibility = View.VISIBLE
            imgUserPassive.visibility = View.VISIBLE
            imgSettingPassive.visibility = View.VISIBLE
            imgPhonePassive.visibility = View.VISIBLE
            changeFragment(ArizaListe())
        }

        imgOther.setOnClickListener {

            imgOtherPassive.visibility = View.INVISIBLE
            imgFaultPassive.visibility = View.VISIBLE
            imgMotorPassive.visibility = View.VISIBLE
            imgUserPassive.visibility = View.VISIBLE
            imgSettingPassive.visibility = View.VISIBLE
            imgPhonePassive.visibility = View.VISIBLE
            changeFragment(DigerBilgiler())


        }

        imgUser.setOnClickListener {

            imgUserPassive.visibility = View.INVISIBLE
            imgOtherPassive.visibility = View.VISIBLE
            imgFaultPassive.visibility = View.VISIBLE
            imgMotorPassive.visibility = View.VISIBLE
            imgSettingPassive.visibility = View.VISIBLE
            imgPhonePassive.visibility = View.VISIBLE
            changeFragment(Kullanicilar())

        }

        imgSetting.setOnClickListener {

            imgSettingPassive.visibility=View.INVISIBLE
            imgUserPassive.visibility = View.VISIBLE
            imgOtherPassive.visibility = View.VISIBLE
            imgFaultPassive.visibility = View.VISIBLE
            imgMotorPassive.visibility = View.VISIBLE
            imgPhonePassive.visibility = View.VISIBLE
            changeFragment(Ayarlar())

        }

    }

    private fun gelenPendingIntent() {

        val gelenIntent = intent

        val gelenbilgiMotorTag = gelenIntent.getStringExtra("kullanici_giris_kayit_sayfasi")

        if (gelenbilgiMotorTag != null){

            val bundle : Bundle? =Bundle()
            bundle?.putString("anaSayfadanGelenMotorTag",gelenbilgiMotorTag)
            val fragment = MotorListe()
            fragment.arguments = bundle
            val transaction : FragmentTransaction? = supportFragmentManager.beginTransaction()
            transaction?.replace(R.id.containerFragment,fragment,"rv_fragment")?.commit()

        }else{

            changeFragment(MotorListe())
        }

    }

    fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerFragment,fragment,"fragment_container")
        fragmentTransaction.commit()
    }
}
