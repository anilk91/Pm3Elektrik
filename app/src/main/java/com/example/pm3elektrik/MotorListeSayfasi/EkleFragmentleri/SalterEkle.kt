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

import com.example.pm3elektrik.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_salter_ekle.*


class SalterEkle : Fragment() {

    val secimListesi = arrayOf("Sürücü Seçiniz", "Kontaktör", "Frekans Konvertörü","IT SoftStarter","EasySoftStarter")
    val ref = FirebaseDatabase.getInstance().reference
    val salter_liste = SalterModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_salter_ekle, container, false)
        val surucuSpinner = view.findViewById<Spinner>(R.id.spinnerSurucuSecim)
        val buttonEkle = view.findViewById<Button>(R.id.buttonSalterEkle)

        surucuSpinner!!.adapter = ArrayAdapter(container!!.context, android.R.layout.simple_spinner_dropdown_item, secimListesi)
        surucuSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position == 1){

                    etSalterModel.visibility = View.GONE

                    etSalterKontaktorCat.visibility = View.VISIBLE
                    etSalterKontaktorDIPSivic.visibility = View.VISIBLE
                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                    val kontaktorCat = etSalterKontaktorCat.text.toString()
                    val dipSivic = etSalterKontaktorDIPSivic.text.toString()
                    val kontaktorDegisimTarihi = etSalterSurucuDegTarihi.text.toString()



                }else if (position == 2){

                    etSalterKontaktorCat.visibility = View.GONE
                    etSalterKontaktorDIPSivic.visibility = View.GONE

                    etSalterModel.visibility = View.VISIBLE
                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                    val surucuModel = etSalterModel.text.toString()
                    val surucuDegisimTarihi = etSalterSurucuDegTarihi




                }else if(position == 3){

                    etSalterKontaktorCat.visibility = View.GONE
                    etSalterKontaktorDIPSivic.visibility = View.GONE

                    etSalterModel.visibility = View.VISIBLE
                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                    val surucuModel = etSalterModel.text.toString()
                    val surucuDegisimTarihi = etSalterSurucuDegTarihi



                }else if(position == 4){

                    etSalterKontaktorCat.visibility = View.GONE
                    etSalterKontaktorDIPSivic.visibility = View.GONE

                    etSalterModel.visibility = View.VISIBLE
                    etSalterSurucuDegTarihi.visibility = View.VISIBLE

                    val surucuModel = etSalterModel.text.toString()
                    val surucuDegisimTarihi = etSalterSurucuDegTarihi



                }else if(position == 0){

                    etSalterKontaktorCat.visibility = View.GONE
                    etSalterKontaktorDIPSivic.visibility = View.GONE
                    etSalterModel.visibility = View.GONE
                    etSalterSurucuDegTarihi.visibility = View.GONE

                }
            }


        }

        buttonEkle.setOnClickListener{

            val motorTag = etSalterMotorTag.text.toString()
            val marka =etSalterMarka.text.toString()
            val kapasite = etSalterKapasite.text.toString()
            val cat = etSalterCAT.text.toString()
            val style = etSalterSTYLE.text.toString()
            val demeraj = etSalterDemeraj.text.toString()
            val degisimTarihi = etSalterDegTarihi.text.toString()
            val mccYeri= etSalterMCCYeri.text.toString()


            if(motorTag.isNotEmpty() && marka.isNotEmpty() && kapasite.isNotEmpty() && mccYeri.isNotEmpty()){

                salter_liste.salterMotorTag = motorTag
                salter_liste.salterMarka = marka
                salter_liste.salterKapasite = kapasite
                salter_liste.salterCAT = cat
                salter_liste.salterSTYLE = style
                salter_liste.salterDemeraj = demeraj
                salter_liste.salterDegTarihi = degisimTarihi
                salter_liste.salterMccYeri = mccYeri





            }else{
                Toast.makeText(activity,"Motor Tag, Marka, Kapasite, ve Mcc Yerini Doldurunuz... ",Toast.LENGTH_LONG).show()
            }



        }




        val button_close = view.findViewById<ImageView>(R.id.imgSalterClose)
        button_close.setOnClickListener {
            changeFragment(MotorListe())
        }


        return view
    }

    private fun changeFragment(fragment : Fragment){
        val fragmentTransaction : FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.containerMotorListe,fragment,"fragment")
        fragmentTransaction.commit()

    }

}
