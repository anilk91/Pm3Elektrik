package com.example.pm3elektrik.MotorListeSayfasi.DriveUniteleriSayfasi.DriveUniteModel

class DriveModel {

    var isim = ""
    var tag = ""
    var guc = 0.0
    var devir = ""
    var tripAkim = ""
    var insaTipi = ""
    var flans = ""
    var adres = ""
    var motorDegTarihi = ""
    var seriNoU = ""
    var seriNoV = ""
    var seriNoW = ""
    var uModulDegTarihi = ""
    var vModulDegTarihi = ""
    var wModulDegTarihi = ""
    var uniteGucKVA = ""



    constructor()
    constructor(
        isim: String,
        tag: String,
        guc: Double,
        devir: String,
        tripAkim: String,
        insaTipi: String,
        flans: String,
        adres: String,
        motorDegTarihi: String,
        seriNoU: String,
        seriNoV: String,
        seriNoW: String,
        uModulDegTarihi: String,
        vModulDegTarihi: String,
        wModulDegTarihi: String,
        uniteGucKVA : String
    ) {
        this.isim = isim
        this.tag = tag
        this.guc = guc
        this.devir = devir
        this.tripAkim = tripAkim
        this.insaTipi = insaTipi
        this.flans = flans
        this.adres = adres
        this.motorDegTarihi = motorDegTarihi
        this.seriNoU = seriNoU
        this.seriNoV = seriNoV
        this.seriNoW = seriNoW
        this.uModulDegTarihi = uModulDegTarihi
        this.vModulDegTarihi = vModulDegTarihi
        this.wModulDegTarihi = wModulDegTarihi
        this.uniteGucKVA = uniteGucKVA
    }


}