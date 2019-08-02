package com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel

class SalterModel {

    var salterMarka = ""
    var salterKapasite =""
    var salterCAT =""
    var salterSTYLE =""
    var salterDemeraj=""
    var salterDegTarihi=""

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