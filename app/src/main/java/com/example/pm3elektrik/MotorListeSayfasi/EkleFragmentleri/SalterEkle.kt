package com.example.pm3elektrik.MotorListeSayfasi.EkleFragmentleri


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.example.pm3elektrik.MotorListeSayfasi.MotorListe
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SalterModel
import com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel.SurucuModel

import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_salter_ekle.*


class SalterEkle : Fragment() {

    val secimListesi = arrayOf("Sürücü Seçiniz", "Kontaktör", "Frekans Konvertörü", "IT SoftStarter", "EasySoftStarter")
    val ref = FirebaseDatabase.getInstance().reference
    val salter_liste = SalterModel()
    val surucu_liste = SurucuModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_salter_ekle, container, false)
        val surucuSpinner = view.findViewById(R.id.spinnerSurucuSecim) as Spinner
        val buttonEkle = view.findViewById<Button>(R.id.buttonSalterEkle)

        surucuSpinner.adapter =
            ArrayAdapter(container!!.context, android.R.layout.simple_spinner_dropdown_item, secimListesi)
        surucuSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position == 1) {

                    etSalterSurucuModel.visibility = View.VISIBLE
                    etSalterKontaktorDIPSivic.visibility = View.VISIBLE
                    etSalterKontaktorBoyut.visibility = View.VISIBLE
                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                    surucu_liste.surucuIsim = "Kontaktör"

                } else if (position == 2) {


                    etSalterKontaktorDIPSivic.visibility = View.GONE
                    etSalterKontaktorBoyut.visibility = View.GONE

                    etSalterSurucuModel.visibility = View.VISIBLE
                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                    surucu_liste.surucuIsim = "Frekans Konvertörü"

                } else if (position == 3) {


                    etSalterKontaktorDIPSivic.visibility = View.GONE
                    etSalterKontaktorBoyut.visibility = View.GONE

                    etSalterSurucuModel.visibility = View.VISIBLE
                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                    surucu_liste.surucuIsim = "IT SoftStarter"


                } else if (position == 4) {


                    etSalterKontaktorDIPSivic.visibility = View.GONE
                    etSalterKontaktorBoyut.visibility = View.GONE

                    etSalterSurucuModel.visibility = View.VISIBLE
                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                    etSalterKontaktorBoyut.editableText.clear()
                    etSalterKontaktorDIPSivic.editableText.clear()

                    surucu_liste.surucuIsim = "Easy SoftStarter"


                } else if (position == 0) {

                    etSalterKontaktorDIPSivic.visibility = View.GONE
                    etSalterKontaktorBoyut.visibility = View.GONE
                    etSalterSurucuModel.visibility = View.GONE
                    etSalterSurucuDegTarihi.visibility = View.GONE
                }
            }
        }

        buttonEkle.setOnClickListener {

            val motorTag = etMotorTag.text.toString().toUpperCase()
            val marka = etSalterMarka.text.toString().toUpperCase()
            val kapasite = etSalterKapasite.text.toString().toUpperCase()
            val cat = etSalterCAT.text.toString().toUpperCase()
            val style = etSalterSTYLE.text.toString().toUpperCase()
            val demeraj = etSalterDemeraj.text.toString().toUpperCase()
            val degisimTarihi = etSalterDegTarihi.text.toString().toUpperCase()
            val mccYeri = etSalterMCCYeri.text.toString().toUpperCase()


            val dipSivic = etSalterKontaktorDIPSivic.text.toString().toUpperCase()
            val kontaktorBoyut = etSalterKontaktorBoyut.text.toString().toUpperCase()
            val surucuModel = etSalterSurucuModel.text.toString().toUpperCase()
            val surucuDegisimTarihi = etSalterSurucuDegTarihi.text.toString().toUpperCase()

            if (motorTag.isNotEmpty()) {

                salter_liste.salterMotorTag = motorTag
                salter_liste.salterMarka = marka
                salter_liste.salterKapasite = kapasite
                salter_liste.salterCAT = cat
                salter_liste.salterSTYLE = style
                salter_liste.salterDemeraj = demeraj
                salter_liste.salterDegTarihi = degisimTarihi
                salter_liste.salterMccYeri = mccYeri

                surucu_liste.surucuBoyut = kontaktorBoyut
                surucu_liste.surucuDIPSivic = dipSivic

                surucu_liste.surucuDegTarihi = surucuDegisimTarihi
                surucu_liste.surucuModel = surucuModel

                ref.child("pm3Elektrik")
                    .child("Salter")
                    .child(motorTag)
                    .setValue(salter_liste)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(activity, "Kayıt Yapıldı", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(
                                activity,
                                "Kayıt Yapılamadı Hata:${it.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                ref.child("pm3Elektrik")
                    .child("Surucu")
                    .child(motorTag)
                    .setValue(surucu_liste)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(activity, "Kayıt Yapıldı", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(activity, "Kayıt Yapılamadı ${it.exception?.message}", Toast.LENGTH_LONG)
                                .show()
                        }

                    }


            } else {
                Toast.makeText(activity, "Motor Tag Alanını Doldurunuz... ", Toast.LENGTH_LONG).show()
            }
        }

        val button_close = view.findViewById<ImageView>(R.id.imgSalterClose)
        button_close.setOnClickListener {
            changeFragment(MotorListe())
        }


        return view
    }
    private fun changeFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe, fragment, "fragment")
        fragmentTransaction.commit()

    }
}
