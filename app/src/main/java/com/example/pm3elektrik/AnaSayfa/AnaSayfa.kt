package com.example.pm3elektrik.AnaSayfa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.R

class AnaSayfa : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ana_sayfa)

        changeFragment(MotorListe())

    }



    fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerFragment,fragment,"fragment_container")
        fragmentTransaction.commit()
    }
}
