package com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.UniteNotEkle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUnite
import com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel.UniteNotuModel

import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class DriveUniteNotEkle : DialogFragment() {

    val ref = FirebaseDatabase.getInstance().reference.child("pm3Elektrik").child("DriveUniteNot")
    val notListesi = UniteNotuModel()
    var motorTag: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drive_unite_not_ekle, container, false)

        val close = view.findViewById<ImageView>(R.id.imgDriveUniteNotEkleClose)
        val ekle = view.findViewById<Button>(R.id.btnDriveUniteNotEkle)

        val bundle: Bundle? = arguments
        motorTag = bundle?.getString("driveUniteGelenTag")

        close.setOnClickListener { dismiss() }

        ekle.setOnClickListener {

            val not = view.findViewById<EditText>(R.id.etDriveUniteNotEkle).text.toString()
            val tarih = view.findViewById<EditText>(R.id.etDriveUniteTarihEkle).text.toString()

            notListesi.uniteNotu = not
            notListesi.uniteNotuTarih = tarih
            notListesi.sistemSaat = saat()



            if (not.isNotEmpty() && tarih.isNotEmpty()) {
                ref.child(motorTag!!).child(saat()).setValue(notListesi)
                    .addOnCompleteListener {
                        if (it.isComplete) {

                        } else {
                            try {
                                Toast.makeText(activity, "Kayıt Yapılamadı Hata: ${it.exception?.message}", Toast.LENGTH_LONG).show()
                            } catch (hata: Exception) {
                                Toast.makeText(activity, "Hata: ${hata.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                Toast.makeText(activity, "Başarılı", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity, "Boş Alanları Doldurunuz", Toast.LENGTH_LONG).show()
            }

            degistir(DriveUnite())
        }
        return view
    }

    private fun saat(): String {

        val lokalZaman = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MMMM EEEE HH:mm:ss", Locale("tr"))
        val zaman = formatter.format(lokalZaman)

        return zaman

    }

    private fun changeFragment(fragment : Fragment){

        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"drive_unite_ekle")
        fragmentTransaction.commit()

    }

    private fun degistir(fragment : Fragment){

        val bundle : Bundle? =Bundle()
        bundle?.putString("rvGelenMotorTag",motorTag)
        val fragment = DriveUnite()
        fragment.arguments = bundle
        val transaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.containerDriveUnite,fragment,"drive_unite_fr")?.commit()
    }


}
