package com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel

class MotorModel {


    var motorIsim: String = ""
    var motorTag: String = ""
    var motorGucKW: Double = 0.0
    var motorGucHP: Double = 0.0
    var motorDevir: String = "-"
    var motorNomTripAkimi: String = ""
    var motorInsaTipi: String = ""
    var motorFlans: String = ""
    var motorAdres: String = ""
    var motorMCCYeri: String = ""
    var motorDegTarihi: String = ""

    constructor(motorIsim : String ,motorTag: String, motorGucKW: Double, motorGucHP: Double, motorDevir: String, motorNomTripAkimi: String,
        motorInsaTipi: String, motorFlans: String, motorAdres: String, motorMCCYeri: String, motorDegTarihi: String
    ) {

        this.motorIsim = motorIsim
        this.motorTag = motorTag
        this.motorGucKW = motorGucKW
        this.motorGucHP = motorGucHP
        this.motorDevir = motorDevir
        this.motorNomTripAkimi = motorNomTripAkimi
        this.motorInsaTipi = motorInsaTipi
        this.motorFlans = motorFlans
        this.motorAdres = motorAdres
        this.motorMCCYeri = motorMCCYeri
        this.motorDegTarihi = motorDegTarihi
    }

    constructor(motorTag: String, motorMCCYeri: String, motorGucKW: Double, motorDevir: String) {

        this.motorTag = motorTag
        this.motorMCCYeri = motorMCCYeri
        this.motorGucKW = motorGucKW
        this.motorDevir = motorDevir

    }

    constructor()


}