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
    var motorNot = ""

    //-----ÇEKMECE ŞALTER İÇERİK OLANLAR-----
    //motorTag push() metodu olucak
    //Diğer bilgiler değer olucak
    //motorMCCYeride kullanılacak

    var cekmeceIsim = ""
    var cekmeceMarka = ""
    var cekmeceModel = ""
    var cekmeceKapasite = ""
    var cekmeceCat = ""
    var cekmeceSalterDegisim=""
    var cekmeceDemeraj = ""


    constructor(motorIsim : String ,motorTag: String, motorGucKW: Double, motorGucHP: Double, motorDevir: String, motorNomTripAkimi: String,
        motorInsaTipi: String, motorFlans: String, motorAdres: String, motorMCCYeri: String, motorDegTarihi: String, motorNot:String
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
        this.motorNot = motorNot
    }


    constructor(motorTag: String, motorMCCYeri: String, motorGucKW: Double, motorDevir: String) {

        this.motorTag = motorTag
        this.motorMCCYeri = motorMCCYeri
        this.motorGucKW = motorGucKW
        this.motorDevir = motorDevir

    }

    constructor(motorTag: String, motorMCCYeri: String, cekmeceIsim: String, cekmeceMarka: String, cekmeceModel: String, cekmeceKapasite: String, cekmeceCat: String, cekmeceSalterDegisim: String, cekmeceDemeraj: String) {

        this.motorTag = motorTag
        this.motorMCCYeri = motorMCCYeri
        this.cekmeceIsim = cekmeceIsim
        this.cekmeceMarka = cekmeceMarka
        this.cekmeceModel = cekmeceModel
        this.cekmeceKapasite = cekmeceKapasite
        this.cekmeceCat = cekmeceCat
        this.cekmeceSalterDegisim = cekmeceSalterDegisim
        this.cekmeceDemeraj = cekmeceDemeraj
    }

    constructor(motorTag: String, motorMCCYeri: String) {
        this.motorTag = motorTag
        this.motorMCCYeri = motorMCCYeri
    }


    constructor()
    constructor(motorTag: String, motorMCCYeri: String, motorGucKW: Double, motorDevir: String, cekmeceModel: String, cekmeceMarka: String, cekmeceKapasite: String) {

        this.motorTag = motorTag
        this.motorMCCYeri = motorMCCYeri
        this.motorGucKW = motorGucKW
        this.motorDevir = motorDevir
        this.cekmeceModel = cekmeceModel
        this.cekmeceMarka = cekmeceMarka
        this.cekmeceKapasite = cekmeceKapasite
    }

}