package com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi.DipSivicVeMotoraGoreAkimSayfasi


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment

import com.example.pm3elektrik.R


class MotorAkiminaGoreDipSivic : DialogFragment() {

    var spinnerListesi = arrayOf("Low Range","Size 1","Size 2","Size 3","Size 4","Size 5")
    var motorAkimListesi = ArrayList<MotorAkimModeli>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_motor_akimina_gore_dip_sivic, container, false)

        val close = view.findViewById<ImageView>(R.id.imgMotorAkimDialogClose)
        val spinnerCekmece = view.findViewById<Spinner>(R.id.spinnerCekmeceBoyut)
        val motorAkimBul = view.findViewById<EditText>(R.id.etMotorAkiminaGore)
        val sonucYaz = view.findViewById<TextView>(R.id.tvBulunanDipSivic)


        spinnerCekmece.adapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_dropdown_item,spinnerListesi)
        spinnerCekmece.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if(p2 == 0){
                    motorAkimBul.addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun afterTextChanged(p0: Editable?) {

                           lowRangeMotorAkim()

                            val gelenVeri = motorAkimBul.text.toString()
                            val arananAkim = ArrayList<MotorAkimModeli>()

                            for (gelen in motorAkimListesi){
                                val bulunanAkim = gelen.akim
                                val sonuc = bulunanAkim.contains(gelenVeri)

                                if (sonuc == true){
                                    Log.e("dipSivic","${gelen.dipSivic}")
                                    
                                    sonucYaz.setText("${gelen.dipSivic}")
                                }
                            }
                        }
                    })

                }else if (p2 == 1){

                }else if (p2 == 3){

                }else if (p2 == 4){

                }else if (p2 == 5){

                }

            }


        }


        return view
    }

    fun lowRangeMotorAkim(){

        motorAkimListesi.add(MotorAkimModeli("0.59","00000"))
        motorAkimListesi.add(MotorAkimModeli("0.65","00001"))
        motorAkimListesi.add(MotorAkimModeli("0.71","00010"))
        motorAkimListesi.add(MotorAkimModeli("0.78","00011"))
        motorAkimListesi.add(MotorAkimModeli("0.86","00100"))


    }
    class MotorAkimModeli {

        var akim = ""
        var dipSivic = ""

        constructor(akim: String, dipSivic: String) {
            this.akim = akim
            this.dipSivic = dipSivic
        }

        constructor()
    }
}


