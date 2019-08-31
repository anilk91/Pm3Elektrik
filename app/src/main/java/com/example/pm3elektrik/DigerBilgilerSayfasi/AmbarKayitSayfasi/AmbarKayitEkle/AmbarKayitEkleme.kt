package com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitEkle


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayit
import com.example.pm3elektrik.DigerBilgilerSayfasi.AmbarKayitSayfasi.AmbarKayitModel.AmbarKayitModeli
import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class AmbarKayitEkleme : DialogFragment() {

    val ambarListeEkle = AmbarKayitModeli()
    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ambar_kayit_ekleme, container, false)

        val close = view.findViewById<ImageView>(R.id.imgAmbarEkleClose)
        val ekle = view.findViewById<Button>(R.id.buttonAmbarEkle)

        val bundle :Bundle? = arguments
        val stokNoBundle = bundle?.getString("rvGidenStokNo")
        val rafNoBundle= bundle?.getString("rvGidenRafNo")
        val tanimBundle = bundle?.getString("rvGidenTanim")

        val stokEditText = view.findViewById<EditText>(R.id.etAmbarStokNo)
        val rafEditText = view.findViewById<EditText>(R.id.etAmbarRafNo)
        val tanimEditText = view.findViewById<EditText>(R.id.etAmbarTanim)

        stokEditText.setText(stokNoBundle)
        rafEditText.setText(rafNoBundle)
        tanimEditText.setText(tanimBundle)

        close.setOnClickListener {
            dismiss()
        }
        ekle.setOnClickListener {

            val stokNo = view.findViewById<EditText>(R.id.etAmbarStokNo).text.toString()
            val rafNo = view.findViewById<EditText>(R.id.etAmbarRafNo).text.toString().toUpperCase()
            val tanim = view.findViewById<EditText>(R.id.etAmbarTanim).text.toString().toUpperCase()

            if (stokNo.isNotEmpty() && rafNo.isNotEmpty() && tanim.isNotEmpty()) {

                changeFragment(AmbarKayit())

                ambarListeEkle.ambarStokNo = stokNo
                ambarListeEkle.ambarRafNo = rafNo
                ambarListeEkle.ambarTanim = tanim

                val s1 = stokNo.substring(0..0)
                val s2 = stokNo.substring(2..stokNo.lastIndex)
                val stokNoSonHal = s1+s2

                ref.child("Ambar")
                    .child(stokNoSonHal)
                    .setValue(ambarListeEkle)
                    .addOnCompleteListener {

                        if(it.isComplete){

                        }else {
                            try {
                                Toast.makeText(activity,"Kayıt Yapılamadı Hata: ${it.exception?.message}",Toast.LENGTH_LONG).show()
                            }catch (hata : Exception){
                                Toast.makeText(activity,"Hata: ${hata.message}",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
            }
        }

        return view
    }
    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerAmbarKayit,fragment,"ambar_kayit_ekle_fr")
        fragmentTransaction.commit()

    }


}
