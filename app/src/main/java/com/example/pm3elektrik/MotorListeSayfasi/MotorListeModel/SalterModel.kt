package com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel

class SalterModel {

    var salterMotorTag =""
    var salterMarka = ""
    var salterKapasite =""
    var salterCAT =""
    var salterSTYLE =""
    var salterDemeraj=""
    var salterDegTarihi=""
    var salterMccYeri = ""
    var cekmeceDegTarihi =""

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