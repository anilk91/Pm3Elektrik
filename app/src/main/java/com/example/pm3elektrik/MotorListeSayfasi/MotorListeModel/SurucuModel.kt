package com.example.pm3elektrik.MotorListeSayfasi.MotorListeModel

class SurucuModel {

    var surucuIsim ="Bilinmiyor"
    var surucuModel="Bilinmiyor"
    var surucuDegTarihi = "Bilinmiyor"
    var surucuBoyut ="Bilinmiyor"
    var surucuDIPSivic ="Bilinmiyor"

    constructor(surucuIsim : String,surucuModel : String, surucuDegTarihi : String){

        this.surucuIsim = surucuIsim
        this.surucuModel = surucuModel
        this.surucuDegTarihi = surucuDegTarihi
    }

    constructor(surucuIsim : String,surucuModel : String, surucuDegTarihi : String , surucuBoyut : String, surucuDIPSivic : String){

        this.surucuIsim = surucuIsim
        this.surucuModel = surucuModel
        this.surucuDegTarihi = surucuDegTarihi
        this.surucuBoyut = surucuBoyut
        this.surucuDIPSivic = surucuDIPSivic
    }

    constructor()

}