package com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel

class MotorModel {


    var motorTag: String = "Bilinmiyor"
    var motorGucKW: String = "Bilinmiyor"
    var motorGucHP: String = "Bilinmiyor"
    var motorDevir: String = "Bilinmiyor"
    var motorNomTripAkimi: String = "Bilinmiyor"
    var motorInsaTipi: String = "Bilinmiyor"
    var motorFlans: String = "Bilinmiyor"
    var motorAdres: String = "Bilinmiyor"
    var motorMCCYeri: String = "Bilinmiyor"
    var motorDegTarihi: String = "Bilinmiyor"

    constructor(
        motorTag: String, motorGucKW: String, motorGucHP: String, motorDevir: String, motorNomTripAkimi: String,
        motorInsaTipi: String, motorFlans: String, motorAdres: String, motorMCCYeri: String, motorDegTarihi: String
    ) {

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

    constructor(motorTag: String, motorMCCYeri: String, motorGucKW: String, motorDevir: String) {

        this.motorTag = motorTag
        this.motorGucKW = motorGucKW
        this.motorDevir = motorDevir
        this.motorMCCYeri = motorMCCYeri
    }


    constructor(motorTag: String, motorMCCYeri: String){
        this.motorTag = motorTag
        this.motorMCCYeri = motorMCCYeri
    }

    constructor()


}