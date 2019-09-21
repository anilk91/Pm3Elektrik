package com.example.pm3elektrik.DigerBilgilerSayfasi.TrafoSayfasi.TrafoModel

class TrafoModel {

    var trafoIsim = ""
    var trafoDegTarihi = ""
    var trafoNot = ""
    var trafoResimURL = ""

    constructor(trafoIsim: String) {
        this.trafoIsim = trafoIsim
    }

    constructor()

    constructor(
        trafoIsim: String,
        trafoDegTarihi: String,
        trafoNot: String,
        trafoResimURL: String
    ) {
        this.trafoIsim = trafoIsim
        this.trafoDegTarihi = trafoDegTarihi
        this.trafoNot = trafoNot
        this.trafoResimURL = trafoResimURL
    }
}