package com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel

class SalterModel {

    var salterMarka = "Bilinmiyor"
    var salterKapasite ="Bilinmiyor"
    var salterCAT ="Bilinmiyor"
    var salterSTYLE ="Bilinmiyor"
    var salterDemeraj="Bilinmiyor"
    var salterDegTarihi="Bilinmiyor"

    constructor(salterMarka :String, salterKapasite:String,salterCAT: String, salterSTYLE : String,salterDemeraj :String,
                salterDegTarihi : String){

        this.salterMarka = salterMarka
        this.salterKapasite = salterKapasite
        this.salterCAT= salterCAT
        this.salterSTYLE = salterSTYLE
        this.salterDemeraj = salterDemeraj
        this.salterDegTarihi = salterDegTarihi
    }

    constructor()
}