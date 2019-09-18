package com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoModel

class TrafoModel {

    var trafoIsim = ""
    var trafoDegTarihi = ""
    var trafoNot = ""

    constructor(trafoIsim: String) {
        this.trafoIsim = trafoIsim
    }

    constructor(trafoIsim: String, trafoDegTarihi: String, trafoNot: String) {
        this.trafoIsim = trafoIsim
        this.trafoDegTarihi = trafoDegTarihi
        this.trafoNot = trafoNot
    }

    constructor()
}