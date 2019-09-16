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

    var motorGelenVeri = ""

    //-----ÇEKMECE ŞALTER İÇERİK OLANLAR-----
    var cekmeceIsim = ""
    var cekmeceMarka = ""
    var cekmeceModel = ""
    var cekmeceKapasite = ""
    var cekmeceCat = ""
    var cekmeceSalterDegisim=""
    var cekmeceDemeraj = ""
    var cekmeceUid = ""

    //-----DRİVE ÜNİTELERİ İÇERİK------------

    var motorGucKVA = ""



    constructor(motorIsim : String ,motorTag: String, motorGucKW: Double, motorGucHP: Double, motorDevir: String, motorNomTripAkimi: String,
        motorInsaTipi: String, motorFlans: String, motorAdres: String, motorMCCYeri: String, motorDegTarihi: String, motorNot:String, motorGelenVeri : String
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
        this.motorGelenVeri = motorGelenVeri
    }


    constructor(motorTag: String, motorMCCYeri: String, motorGucKW: Double, motorDevir: String) {

        this.motorTag = motorTag
        this.motorMCCYeri = motorMCCYeri
        this.motorGucKW = motorGucKW
        this.motorDevir = motorDevir

    }

    constructor(motorTag: String, motorMCCYeri: String, cekmeceIsim: String, cekmeceMarka: String, cekmeceModel: String, cekmeceKapasite: String, cekmeceCat: String, cekmeceSalterDegisim: String, cekmeceDemeraj: String , motorGelenVeri : String) {

        this.motorTag = motorTag
        this.motorMCCYeri = motorMCCYeri
        this.cekmeceIsim = cekmeceIsim
        this.cekmeceMarka = cekmeceMarka
        this.cekmeceModel = cekmeceModel
        this.cekmeceKapasite = cekmeceKapasite
        this.cekmeceCat = cekmeceCat
        this.cekmeceSalterDegisim = cekmeceSalterDegisim
        this.cekmeceDemeraj = cekmeceDemeraj
        this.motorGelenVeri = motorGelenVeri

    }

    constructor(motorTag: String, motorMCCYeri: String) {
        this.motorTag = motorTag
        this.motorMCCYeri = motorMCCYeri
    }


    constructor()
    constructor(motorTag: String, motorMCCYeri: String, motorGucKW: Double, motorDevir: String, cekmeceModel: String, cekmeceMarka: String, cekmeceKapasite: String , motorGelenVeri : String , motorGucKVA : String , cekmeceUid : String) {

        this.motorTag = motorTag
        this.motorMCCYeri = motorMCCYeri
        this.motorGucKW = motorGucKW
        this.motorDevir = motorDevir
        this.cekmeceModel = cekmeceModel
        this.cekmeceMarka = cekmeceMarka
        this.cekmeceKapasite = cekmeceKapasite
        this.motorGelenVeri = motorGelenVeri
        this.motorGucKVA = motorGucKVA
        this.cekmeceUid = cekmeceUid
    }


}