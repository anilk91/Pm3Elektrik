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


class MotorAkiminaGoreDipSivic : DialogFragment() {

    var spinnerListesi = arrayOf("Low Range","Size 1 ve Size 2","Size 3 ve Size 4","Size 5 ve Size 6")
    var lowRangeListe = ArrayList<MotorAkimModeli>()
    var size1ve2Liste = ArrayList<MotorAkimModeli>()
    var size3ve4Liste = ArrayList<MotorAkimModeli>()
    var size5ve6Liste = ArrayList<MotorAkimModeli>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_motor_akimina_gore_dip_sivic, container, false)

        val close = view.findViewById<ImageView>(R.id.imgMotorAkimDialogClose)
        val spinnerCekmece = view.findViewById<Spinner>(R.id.spinnerCekmeceBoyut)
        val motorAkimBul = view.findViewById(R.id.etMotorAkiminaGore) as AutoCompleteTextView
        val sonucYaz = view.findViewById<TextView>(R.id.tvBulunanDipSivic)

        close.setOnClickListener { dismiss() }

        spinnerCekmece.adapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_dropdown_item,spinnerListesi)
        spinnerCekmece.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if(p2 == 0){
                    motorAkimBul.addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                            val lowRange : Array<out String> = resources.getStringArray(R.array.low_range_trip_deger)
                            val lowRangeAdapter = ArrayAdapter<String>(view.context,android.R.layout.simple_list_item_1,lowRange)
                            motorAkimBul.threshold = 1
                            motorAkimBul.setAdapter(lowRangeAdapter)
                        }
                        override fun afterTextChanged(p0: Editable?) {

                            lowRangeMotorAkim()

                            val gelenVeri = motorAkimBul.text.toString()

                            for (gelen in lowRangeListe){
                                val bulunanAkim = gelen.akim
                                val sonuc = bulunanAkim.contains(gelenVeri)

                                if (sonuc == true){
                                    sonucYaz.setText("${gelen.dipSivic}")
                                }
                            }
                        }
                    })

                }else if (p2 == 1){

                    motorAkimBul.addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                            val size1vesize2 : Array<out String> = resources.getStringArray(R.array.sizeVesize2)
                            val size1ve2adapter = ArrayAdapter<String>(view.context,android.R.layout.simple_list_item_1,size1vesize2)
                            motorAkimBul.threshold = 1
                            motorAkimBul.setAdapter(size1ve2adapter)
                        }
                        override fun afterTextChanged(p0: Editable?) {

                            size1ve2MotorAkim()

                            val gelenVeri = motorAkimBul.text.toString()

                            for (gelen in size1ve2Liste){
                                val bulunanAkim = gelen.akim
                                val sonuc = bulunanAkim.contains(gelenVeri)

                                if (sonuc == true){
                                    sonucYaz.setText("${gelen.dipSivic}")
                                }
                            }
                        }
                    })

                }else if (p2 == 2){

                    motorAkimBul.addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                            val size3vesize4 : Array<out String> = resources.getStringArray(R.array.size3vesize4)
                            val size3vesize4Adapter = ArrayAdapter<String>(view.context,android.R.layout.simple_list_item_1,size3vesize4)
                            motorAkimBul.threshold = 1
                            motorAkimBul.setAdapter(size3vesize4Adapter)
                        }
                        override fun afterTextChanged(p0: Editable?) {

                            size3vesize4MotorAkim()

                            val gelenVeri = motorAkimBul.text.toString()

                            for (gelen in size3ve4Liste){
                                val bulunanAkim = gelen.akim
                                val sonuc = bulunanAkim.contains(gelenVeri)

                                if (sonuc == true){
                                    sonucYaz.setText("${gelen.dipSivic}")
                                }
                            }
                        }
                    })

                }else if (p2 == 3){

                    motorAkimBul.addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                            val size5vesize6 : Array<out String> = resources.getStringArray(R.array.size5vesize6)
                            val size5vesize6Adapter = ArrayAdapter<String>(view.context,android.R.layout.simple_list_item_1,size5vesize6)
                            motorAkimBul.threshold = 1
                            motorAkimBul.setAdapter(size5vesize6Adapter)
                        }
                        override fun afterTextChanged(p0: Editable?) {

                            size5vesize6MotorAkim()

                            val gelenVeri = motorAkimBul.text.toString()

                            for (gelen in size5ve6Liste){
                                val bulunanAkim = gelen.akim
                                val sonuc = bulunanAkim.contains(gelenVeri)

                                if (sonuc == true){
                                    sonucYaz.setText("${gelen.dipSivic}")
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

        lowRangeListe.add(MotorAkimModeli("0.59","00000"))
        lowRangeListe.add(MotorAkimModeli("0.65","00001"))
        lowRangeListe.add(MotorAkimModeli("0.71","00010"))
        lowRangeListe.add(MotorAkimModeli("0.78","00011"))
        lowRangeListe.add(MotorAkimModeli("0.86","00100"))
        lowRangeListe.add(MotorAkimModeli("0.95","00101"))
        lowRangeListe.add(MotorAkimModeli("1.04","00110"))
        lowRangeListe.add(MotorAkimModeli("1.14","00111"))
        lowRangeListe.add(MotorAkimModeli("1.26","01000"))
        lowRangeListe.add(MotorAkimModeli("1.38","01001"))
        lowRangeListe.add(MotorAkimModeli("1.52","01010"))
        lowRangeListe.add(MotorAkimModeli("1.67","01011"))
        lowRangeListe.add(MotorAkimModeli("1.84","01100"))
        lowRangeListe.add(MotorAkimModeli("2.02","01101"))
        lowRangeListe.add(MotorAkimModeli("2.23","01110"))
        lowRangeListe.add(MotorAkimModeli("2.45","01111"))
        lowRangeListe.add(MotorAkimModeli("2.69","10000"))
        lowRangeListe.add(MotorAkimModeli("2.96","10001"))
        lowRangeListe.add(MotorAkimModeli("3.26","10010"))
        lowRangeListe.add(MotorAkimModeli("3.58","10011"))
        lowRangeListe.add(MotorAkimModeli("3.94","10100"))
        lowRangeListe.add(MotorAkimModeli("4.34","10101"))


    }

    private fun size1ve2MotorAkim() {

        size1ve2Liste.add(MotorAkimModeli("3.93","00000"))
        size1ve2Liste.add(MotorAkimModeli("4.33","00001"))
        size1ve2Liste.add(MotorAkimModeli("4.77","00010"))
        size1ve2Liste.add(MotorAkimModeli("5.25","00011"))
        size1ve2Liste.add(MotorAkimModeli("5.77","00100"))
        size1ve2Liste.add(MotorAkimModeli("6.35","00101"))
        size1ve2Liste.add(MotorAkimModeli("6.9","00110"))
        size1ve2Liste.add(MotorAkimModeli("7.7","00111"))
        size1ve2Liste.add(MotorAkimModeli("8.5","01000"))
        size1ve2Liste.add(MotorAkimModeli("9.3","01001"))
        size1ve2Liste.add(MotorAkimModeli("10.2","01010"))
        size1ve2Liste.add(MotorAkimModeli("11.2","01011"))
        size1ve2Liste.add(MotorAkimModeli("12.4","01100"))
        size1ve2Liste.add(MotorAkimModeli("13.6","01101"))
        size1ve2Liste.add(MotorAkimModeli("15.0","01110"))
        size1ve2Liste.add(MotorAkimModeli("16.5","01111"))
        size1ve2Liste.add(MotorAkimModeli("18.1","10000"))
        size1ve2Liste.add(MotorAkimModeli("19.9","10001"))
        size1ve2Liste.add(MotorAkimModeli("21.9","10010"))
        size1ve2Liste.add(MotorAkimModeli("24.1","10011"))
        size1ve2Liste.add(MotorAkimModeli("26.5","10100"))
        size1ve2Liste.add(MotorAkimModeli("29.1","10101"))
        size1ve2Liste.add(MotorAkimModeli("32.1","10110"))
        size1ve2Liste.add(MotorAkimModeli("29.1","10101"))
        size1ve2Liste.add(MotorAkimModeli("32.1","10110"))
        size1ve2Liste.add(MotorAkimModeli("35.3","10111"))
        size1ve2Liste.add(MotorAkimModeli("38.9","11000"))
        size1ve2Liste.add(MotorAkimModeli("42.8","11001"))
        size1ve2Liste.add(MotorAkimModeli("47.0","11010"))
        size1ve2Liste.add(MotorAkimModeli("51.6","11011"))

    }

    private fun size3vesize4MotorAkim() {

        size3ve4Liste.add(MotorAkimModeli("12.4","00000"))
        size3ve4Liste.add(MotorAkimModeli("13.6","00001"))
        size3ve4Liste.add(MotorAkimModeli("15.0","00010"))
        size3ve4Liste.add(MotorAkimModeli("16.5","00011"))
        size3ve4Liste.add(MotorAkimModeli("18.1","00100"))
        size3ve4Liste.add(MotorAkimModeli("19.9","00101"))
        size3ve4Liste.add(MotorAkimModeli("21.9","00110"))
        size3ve4Liste.add(MotorAkimModeli("24.1","00111"))
        size3ve4Liste.add(MotorAkimModeli("26.5","01000"))
        size3ve4Liste.add(MotorAkimModeli("29.1","01001"))
        size3ve4Liste.add(MotorAkimModeli("32.1","01010"))
        size3ve4Liste.add(MotorAkimModeli("35.3","01011"))
        size3ve4Liste.add(MotorAkimModeli("38.8","01100"))
        size3ve4Liste.add(MotorAkimModeli("42.7","01101"))
        size3ve4Liste.add(MotorAkimModeli("47.0","01110"))
        size3ve4Liste.add(MotorAkimModeli("51.7","01111"))
        size3ve4Liste.add(MotorAkimModeli("56.9","10000"))
        size3ve4Liste.add(MotorAkimModeli("62.6","10001"))
        size3ve4Liste.add(MotorAkimModeli("68.8","10010"))
        size3ve4Liste.add(MotorAkimModeli("75.7","10011"))
        size3ve4Liste.add(MotorAkimModeli("83.3","10100"))
        size3ve4Liste.add(MotorAkimModeli("91.6","10101"))
        size3ve4Liste.add(MotorAkimModeli("101","10110"))
        size3ve4Liste.add(MotorAkimModeli("111","10111"))
        size3ve4Liste.add(MotorAkimModeli("101","10110"))
        size3ve4Liste.add(MotorAkimModeli("111","10111"))
        size3ve4Liste.add(MotorAkimModeli("122","11000"))
        size3ve4Liste.add(MotorAkimModeli("134","11001"))
        size3ve4Liste.add(MotorAkimModeli("147","11010"))
        size3ve4Liste.add(MotorAkimModeli("162","11011"))


    }

    private fun size5vesize6MotorAkim() {

        size5ve6Liste.add(MotorAkimModeli("47.9","00000"))
        size5ve6Liste.add(MotorAkimModeli("52.5","00001"))
        size5ve6Liste.add(MotorAkimModeli("57.7","00010"))
        size5ve6Liste.add(MotorAkimModeli("63.9","00011"))
        size5ve6Liste.add(MotorAkimModeli("70.0","00100"))
        size5ve6Liste.add(MotorAkimModeli("77.3","00101"))
        size5ve6Liste.add(MotorAkimModeli("84.5","00110"))
        size5ve6Liste.add(MotorAkimModeli("93.7","00111"))
        size5ve6Liste.add(MotorAkimModeli("103","01000"))
        size5ve6Liste.add(MotorAkimModeli("113","01001"))
        size5ve6Liste.add(MotorAkimModeli("125","01010"))
        size5ve6Liste.add(MotorAkimModeli("137","01011"))
        size5ve6Liste.add(MotorAkimModeli("151","01100"))
        size5ve6Liste.add(MotorAkimModeli("166","01101"))
        size5ve6Liste.add(MotorAkimModeli("182","01110"))
        size5ve6Liste.add(MotorAkimModeli("200","01111"))
        size5ve6Liste.add(MotorAkimModeli("220","10000"))
        size5ve6Liste.add(MotorAkimModeli("242","10001"))
        size5ve6Liste.add(MotorAkimModeli("267","10010"))
        size5ve6Liste.add(MotorAkimModeli("293","10011"))
        size5ve6Liste.add(MotorAkimModeli("322","10100"))
        size5ve6Liste.add(MotorAkimModeli("293","10011"))
        size5ve6Liste.add(MotorAkimModeli("322","10100"))
        size5ve6Liste.add(MotorAkimModeli("354","10101"))
        size5ve6Liste.add(MotorAkimModeli("390","10110"))
        size5ve6Liste.add(MotorAkimModeli("429","10111"))
        size5ve6Liste.add(MotorAkimModeli("471","11000"))
        size5ve6Liste.add(MotorAkimModeli("519","11001"))
        size5ve6Liste.add(MotorAkimModeli("571","11010"))
        size5ve6Liste.add(MotorAkimModeli("628","11011"))

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


