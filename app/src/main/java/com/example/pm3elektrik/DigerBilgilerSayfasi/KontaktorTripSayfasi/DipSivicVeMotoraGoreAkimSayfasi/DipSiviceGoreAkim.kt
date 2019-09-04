package com.example.pm3elektrik.DigerBilgilerSayfasi.KontaktorTripSayfasi.DipSivicVeMotoraGoreAkimSayfasi


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment

import com.example.pm3elektrik.R

class DipSiviceGoreAkim : DialogFragment() {

    var kontaktorSecim = arrayOf("Low Range","Size 1 ve Size 2","Size 3 ve Size 4","Size 5 ve Size 6")
    var lowRangeListe = ArrayList<DipSivicModel>()
    var size1ve2Liste = ArrayList<DipSivicModel>()
    var size3ve4Liste = ArrayList<DipSivicModel>()
    var size5ve6Liste = ArrayList<DipSivicModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_dip_sivice_gore_akim, container, false)

        val close = view.findViewById<ImageView>(R.id.imgDipSiviceGoreClose)
        val spinnerCekmece = view.findViewById<Spinner>(R.id.spinnerDipSiviceGore)
        val dipSiviceGoreAra = view.findViewById<EditText>(R.id.etDipSiviceGoreAra)
        val sonucYaz = view.findViewById<TextView>(R.id.tvDipSivicSonucu)

        close.setOnClickListener { dismiss() }

        spinnerCekmece.adapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_dropdown_item,kontaktorSecim)
        spinnerCekmece.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                if (p2 == 0){

                    dipSiviceGoreAra.addTextChangedListener( object : TextWatcher{
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun afterTextChanged(p0: Editable?) {

                            lowRangeMotorAkim()

                            val gelenVeri = dipSiviceGoreAra.text.toString()

                            for (gelen in lowRangeListe){
                                val bulunanDipSivic = gelen.dipSivic
                                val sonuc = bulunanDipSivic.contains(gelenVeri)

                                if (sonuc == true){
                                    sonucYaz.setText("${gelen.motorAkim} A")
                                }
                            }

                        }
                    })
                }else if (p2 == 1){

                    dipSiviceGoreAra.addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun afterTextChanged(p0: Editable?) {

                            size1ve2MotorAkim()

                            val gelenVeri = dipSiviceGoreAra.text.toString()

                            for (gelen in size1ve2Liste){
                                val bulunanDipSivic = gelen.dipSivic
                                val sonuc = bulunanDipSivic.contains(gelenVeri)

                                if (sonuc == true){
                                    sonucYaz.setText("${gelen.motorAkim} A")
                                }
                            }
                        }
                    })

                }else if (p2 == 2){

                    dipSiviceGoreAra.addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun afterTextChanged(p0: Editable?) {

                            size3vesize4MotorAkim()

                            val gelenVeri = dipSiviceGoreAra.text.toString()

                            for (gelen in size3ve4Liste){
                                val bulunanDipSivic = gelen.dipSivic
                                val sonuc = bulunanDipSivic.contains(gelenVeri)

                                if (sonuc == true){
                                    sonucYaz.setText("${gelen.motorAkim} A")
                                }
                            }
                        }
                    })

                }else if (p2 == 3){

                    dipSiviceGoreAra.addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun afterTextChanged(p0: Editable?) {

                            size5vesize6MotorAkim()

                            val gelenVeri = dipSiviceGoreAra.text.toString()

                            for (gelen in size5ve6Liste){
                                val bulunanDipSivic = gelen.dipSivic
                                val sonuc = bulunanDipSivic.contains(gelenVeri)

                                if (sonuc == true){
                                    sonucYaz.setText("${gelen.motorAkim} A")
                                }
                            }
                        }
                    })

                }
            }
        }

        return view
    }

    private fun lowRangeMotorAkim(){

        lowRangeListe.add(DipSivicModel("0.59","00000"))
        lowRangeListe.add(DipSivicModel("0.65","00001"))
        lowRangeListe.add(DipSivicModel("0.71","00010"))
        lowRangeListe.add(DipSivicModel("0.78","00011"))
        lowRangeListe.add(DipSivicModel("0.86","00100"))
        lowRangeListe.add(DipSivicModel("0.95","00101"))
        lowRangeListe.add(DipSivicModel("1.04","00110"))
        lowRangeListe.add(DipSivicModel("1.14","00111"))
        lowRangeListe.add(DipSivicModel("1.26","01000"))
        lowRangeListe.add(DipSivicModel("1.38","01001"))
        lowRangeListe.add(DipSivicModel("1.52","01010"))
        lowRangeListe.add(DipSivicModel("1.67","01011"))
        lowRangeListe.add(DipSivicModel("1.84","01100"))
        lowRangeListe.add(DipSivicModel("2.02","01101"))
        lowRangeListe.add(DipSivicModel("2.23","01110"))
        lowRangeListe.add(DipSivicModel("2.45","01111"))
        lowRangeListe.add(DipSivicModel("2.69","10000"))
        lowRangeListe.add(DipSivicModel("2.96","10001"))
        lowRangeListe.add(DipSivicModel("3.26","10010"))
        lowRangeListe.add(DipSivicModel("3.58","10011"))
        lowRangeListe.add(DipSivicModel("3.94","10100"))
        lowRangeListe.add(DipSivicModel("4.34","10101"))


    }

    private fun size1ve2MotorAkim() {

        size1ve2Liste.add(DipSivicModel("3.93","00000"))
        size1ve2Liste.add(DipSivicModel("4.33","00001"))
        size1ve2Liste.add(DipSivicModel("4.77","00010"))
        size1ve2Liste.add(DipSivicModel("5.25","00011"))
        size1ve2Liste.add(DipSivicModel("5.77","00100"))
        size1ve2Liste.add(DipSivicModel("6.35","00101"))
        size1ve2Liste.add(DipSivicModel("6.9","00110"))
        size1ve2Liste.add(DipSivicModel("7.7","00111"))
        size1ve2Liste.add(DipSivicModel("8.5","01000"))
        size1ve2Liste.add(DipSivicModel("9.3","01001"))
        size1ve2Liste.add(DipSivicModel("10.2","01010"))
        size1ve2Liste.add(DipSivicModel("11.2","01011"))
        size1ve2Liste.add(DipSivicModel("12.4","01100"))
        size1ve2Liste.add(DipSivicModel("13.6","01101"))
        size1ve2Liste.add(DipSivicModel("15.0","01110"))
        size1ve2Liste.add(DipSivicModel("16.5","01111"))
        size1ve2Liste.add(DipSivicModel("18.1","10000"))
        size1ve2Liste.add(DipSivicModel("19.9","10001"))
        size1ve2Liste.add(DipSivicModel("21.9","10010"))
        size1ve2Liste.add(DipSivicModel("24.1","10011"))
        size1ve2Liste.add(DipSivicModel("26.5","10100"))
        size1ve2Liste.add(DipSivicModel("29.1","10101"))
        size1ve2Liste.add(DipSivicModel("32.1","10110"))
        size1ve2Liste.add(DipSivicModel("29.1","10101"))
        size1ve2Liste.add(DipSivicModel("32.1","10110"))
        size1ve2Liste.add(DipSivicModel("35.3","10111"))
        size1ve2Liste.add(DipSivicModel("38.9","11000"))
        size1ve2Liste.add(DipSivicModel("42.8","11001"))
        size1ve2Liste.add(DipSivicModel("47.0","11010"))
        size1ve2Liste.add(DipSivicModel("51.6","11011"))

    }

    private fun size3vesize4MotorAkim() {

        size3ve4Liste.add(DipSivicModel("12.4","00000"))
        size3ve4Liste.add(DipSivicModel("13.6","00001"))
        size3ve4Liste.add(DipSivicModel("15.0","00010"))
        size3ve4Liste.add(DipSivicModel("16.5","00011"))
        size3ve4Liste.add(DipSivicModel("18.1","00100"))
        size3ve4Liste.add(DipSivicModel("19.9","00101"))
        size3ve4Liste.add(DipSivicModel("21.9","00110"))
        size3ve4Liste.add(DipSivicModel("24.1","00111"))
        size3ve4Liste.add(DipSivicModel("26.5","01000"))
        size3ve4Liste.add(DipSivicModel("29.1","01001"))
        size3ve4Liste.add(DipSivicModel("32.1","01010"))
        size3ve4Liste.add(DipSivicModel("35.3","01011"))
        size3ve4Liste.add(DipSivicModel("38.8","01100"))
        size3ve4Liste.add(DipSivicModel("42.7","01101"))
        size3ve4Liste.add(DipSivicModel("47.0","01110"))
        size3ve4Liste.add(DipSivicModel("51.7","01111"))
        size3ve4Liste.add(DipSivicModel("56.9","10000"))
        size3ve4Liste.add(DipSivicModel("62.6","10001"))
        size3ve4Liste.add(DipSivicModel("68.8","10010"))
        size3ve4Liste.add(DipSivicModel("75.7","10011"))
        size3ve4Liste.add(DipSivicModel("83.3","10100"))
        size3ve4Liste.add(DipSivicModel("91.6","10101"))
        size3ve4Liste.add(DipSivicModel("101","10110"))
        size3ve4Liste.add(DipSivicModel("111","10111"))
        size3ve4Liste.add(DipSivicModel("101","10110"))
        size3ve4Liste.add(DipSivicModel("111","10111"))
        size3ve4Liste.add(DipSivicModel("122","11000"))
        size3ve4Liste.add(DipSivicModel("134","11001"))
        size3ve4Liste.add(DipSivicModel("147","11010"))
        size3ve4Liste.add(DipSivicModel("162","11011"))


    }

    private fun size5vesize6MotorAkim() {

        size5ve6Liste.add(DipSivicModel("47.9","00000"))
        size5ve6Liste.add(DipSivicModel("52.5","00001"))
        size5ve6Liste.add(DipSivicModel("57.7","00010"))
        size5ve6Liste.add(DipSivicModel("63.9","00011"))
        size5ve6Liste.add(DipSivicModel("70.0","00100"))
        size5ve6Liste.add(DipSivicModel("77.3","00101"))
        size5ve6Liste.add(DipSivicModel("84.5","00110"))
        size5ve6Liste.add(DipSivicModel("93.7","00111"))
        size5ve6Liste.add(DipSivicModel("103","01000"))
        size5ve6Liste.add(DipSivicModel("113","01001"))
        size5ve6Liste.add(DipSivicModel("125","01010"))
        size5ve6Liste.add(DipSivicModel("137","01011"))
        size5ve6Liste.add(DipSivicModel("151","01100"))
        size5ve6Liste.add(DipSivicModel("166","01101"))
        size5ve6Liste.add(DipSivicModel("182","01110"))
        size5ve6Liste.add(DipSivicModel("200","01111"))
        size5ve6Liste.add(DipSivicModel("220","10000"))
        size5ve6Liste.add(DipSivicModel("242","10001"))
        size5ve6Liste.add(DipSivicModel("267","10010"))
        size5ve6Liste.add(DipSivicModel("293","10011"))
        size5ve6Liste.add(DipSivicModel("322","10100"))
        size5ve6Liste.add(DipSivicModel("293","10011"))
        size5ve6Liste.add(DipSivicModel("322","10100"))
        size5ve6Liste.add(DipSivicModel("354","10101"))
        size5ve6Liste.add(DipSivicModel("390","10110"))
        size5ve6Liste.add(DipSivicModel("429","10111"))
        size5ve6Liste.add(DipSivicModel("471","11000"))
        size5ve6Liste.add(DipSivicModel("519","11001"))
        size5ve6Liste.add(DipSivicModel("571","11010"))
        size5ve6Liste.add(DipSivicModel("628","11011"))

    }

    class DipSivicModel{

        var motorAkim = ""
        var dipSivic = ""

        constructor(motorAkim: String, dipSivic: String) {
            this.dipSivic = dipSivic
            this.motorAkim = motorAkim
        }
        constructor()
    }


}
