package com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel

class SalterModel {

    var salterMotorTag ="Bilinmiyor"
    var salterMarka = "Bilinmiyor"
    var salterKapasite ="Bilinmiyor"
    var salterCAT ="Bilinmiyor"
    var salterSTYLE ="Bilinmiyor"
    var salterDemeraj="Bilinmiyor"
    var salterDegTarihi="Bilinmiyor"
    var salterMccYeri = "Bilinmiyor"
    var cekmeceDegTarihi ="Bilinmiyor"

    constructor(salterMotorTag : String, salterMarka :String, salterKapasite:String,salterCAT: String, salterSTYLE : String,salterDemeraj :String,
                salterDegTarihi : String , salterMccYeri : String , cekmeceDegTarihi : String){

        this.salterMotorTag = salterMotorTag
        this.salterMarka = salterMarka
        this.salterKapasite = salterKapasite
        this.salterCAT= salterCAT
        this.salterSTYLE = salterSTYLE
        this.salterDemeraj = salterDemeraj
        this.salterDegTarihi = salterDegTarihi
        this.salterMccYeri = salterMccYeri
        this.cekmeceDegTarihi = cekmeceDegTarihi
    }

    constructor()
}