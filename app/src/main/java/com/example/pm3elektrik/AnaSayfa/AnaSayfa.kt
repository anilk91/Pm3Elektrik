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
import com.example.pm3elektrik.MotorListeSayfasi.CekmecesiSalterOlanSayfa.CekmecesiSalterOlanEtiket
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUnite
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.R
import com.example.pm3elektrik.TelefonListeSayfasi.TelefonListesi
import com.example.pm3elektrik.VersiyonBilgiDialogSayfasi.VersiyonBilgiDialog
import kotlinx.android.synthetic.main.activity_ana_sayfa.*

class AnaSayfa : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ana_sayfa)

        val sharedPref = getSharedPreferences("versiyon_bilgi_pref",0)
        val ilkAcilis = sharedPref.getBoolean("versiyon_bilgi",true)

        if (ilkAcilis == true){
            versiyonBilgiFragment()
        }

        imgMotorPassive.visibility = View.INVISIBLE
        gelenPendingIntent()

        imgMotorPassive.setOnClickListener {

            imgMotorPassive.visibility = View.INVISIBLE
            imgPhonePassive.visibility = View.VISIBLE
            imgFaultPassive.visibility = View.VISIBLE
            imgOtherPassive.visibility = View.VISIBLE
//            imgUserPassive.visibility = View.VISIBLE
            imgAyarlarPassive.visibility = View.VISIBLE
            changeFragment(MotorListe())

        }

        imgPhonePassive.setOnClickListener{

            imgPhonePassive.visibility = View.INVISIBLE
            imgMotorPassive.visibility = View.VISIBLE
            imgFaultPassive.visibility = View.VISIBLE
            imgOtherPassive.visibility = View.VISIBLE
//            imgUserPassive.visibility = View.VISIBLE
            imgAyarlarPassive.visibility = View.VISIBLE
            changeFragment(TelefonListesi())
        }

        imgFault.setOnClickListener {

            imgFaultPassive.visibility = View.INVISIBLE
            imgMotorPassive.visibility = View.VISIBLE
            imgOtherPassive.visibility = View.VISIBLE
//            imgUserPassive.visibility = View.VISIBLE
            imgAyarlarPassive.visibility = View.VISIBLE
            imgPhonePassive.visibility = View.VISIBLE
            changeFragment(ArizaListe())
        }

        imgOther.setOnClickListener {

            imgOtherPassive.visibility = View.INVISIBLE
            imgFaultPassive.visibility = View.VISIBLE
            imgMotorPassive.visibility = View.VISIBLE
//            imgUserPassive.visibility = View.VISIBLE
            imgAyarlarPassive.visibility = View.VISIBLE
            imgPhonePassive.visibility = View.VISIBLE
            changeFragment(DigerBilgiler())


        }

//        imgUser.setOnClickListener {
//
//            imgUserPassive.visibility = View.INVISIBLE
//            imgOtherPassive.visibility = View.VISIBLE
//            imgFaultPassive.visibility = View.VISIBLE
//            imgMotorPassive.visibility = View.VISIBLE
//            imgSettingPassive.visibility = View.VISIBLE
//            imgPhonePassive.visibility = View.VISIBLE
//            changeFragment(Kullanicilar())
//
//        }

        imgAyarlar.setOnClickListener {

            imgAyarlarPassive.visibility=View.INVISIBLE
//            imgUserPassive.visibility = View.VISIBLE
            imgOtherPassive.visibility = View.VISIBLE
            imgFaultPassive.visibility = View.VISIBLE
            imgMotorPassive.visibility = View.VISIBLE
            imgPhonePassive.visibility = View.VISIBLE
            changeFragment(Ayarlar())

        }

    }

    private fun gelenPendingIntent() {

        val gelenIntent = intent

        val gelenTur = gelenIntent.getStringExtra("gelenTur")
        val gelenTag = gelenIntent.getStringExtra("gelenTag")

        if (gelenTur != null){

            if (gelenTur == "motor"){
                val bundle : Bundle? =Bundle()
                bundle?.putString("anaSayfadanGelenMotorTag",gelenTag)
                val fragment = MotorListe()
                fragment.arguments = bundle
                val transaction : FragmentTransaction? = supportFragmentManager.beginTransaction()
                transaction?.replace(R.id.containerFragment,fragment,"rv_fragment")?.commit()

            }else if(gelenTur == "telefon"){
                val fragment = TelefonListesi()
                val transaction : FragmentTransaction? = supportFragmentManager.beginTransaction()
                transaction?.replace(R.id.containerFragment,fragment,"rv_fragment")?.commit()

                imgPhonePassive.visibility = View.INVISIBLE
                imgMotorPassive.visibility = View.VISIBLE
                imgFaultPassive.visibility = View.VISIBLE
                imgOtherPassive.visibility = View.VISIBLE
//                imgUserPassive.visibility = View.VISIBLE
                imgAyarlarPassive.visibility = View.VISIBLE
            }else if (gelenTur == "cekmece"){

                val bundle : Bundle? =Bundle()
                bundle?.putString("rvGelenTag",gelenTag)
                val fragment = CekmecesiSalterOlanEtiket()
                fragment.arguments = bundle
                val transaction : FragmentTransaction? = supportFragmentManager.beginTransaction()
                transaction?.replace(R.id.containerFragment,fragment,"rv_fragment")?.commit()

            }else if (gelenTur == "drive"){

                val bundle : Bundle? =Bundle()
                bundle?.putString("rvGelenMotorTag",gelenTag)
                val fragment = DriveUnite()
                fragment.arguments = bundle
                val transaction : FragmentTransaction? = supportFragmentManager.beginTransaction()
                transaction?.replace(R.id.containerFragment,fragment,"rv_fragment")?.commit()

            }else if(gelenTur == "ambar"){

                imgOtherPassive.visibility = View.INVISIBLE
                imgFaultPassive.visibility = View.VISIBLE
                imgMotorPassive.visibility = View.VISIBLE
//                imgUserPassive.visibility = View.VISIBLE
                imgAyarlarPassive.visibility = View.VISIBLE
                imgPhonePassive.visibility = View.VISIBLE

                val bundle : Bundle? =Bundle()
                bundle?.putString("DigerBilgilereGir",gelenTag)
                val fragment = DigerBilgiler()
                fragment.arguments = bundle
                val transaction : FragmentTransaction? = supportFragmentManager.beginTransaction()
                transaction?.replace(R.id.containerFragment,fragment,"rv_fragment")?.commit()
            }


        }else{

            changeFragment(MotorListe())
        }

    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerFragment,fragment,"fragment_container")
        fragmentTransaction.commit()
    }

    private fun versiyonBilgiFragment(){

        val versiyonBilgisi = VersiyonBilgiDialog()
        versiyonBilgisi.show(supportFragmentManager,"versiyon_bilgisi_dialog_fr")

        val sharedPref = getSharedPreferences("versiyon_bilgi_pref",0)
        val editor = sharedPref.edit()
        editor.putBoolean("versiyon_bilgi",false)
        editor.apply()


    }
}
