package com.example.pm3elektrik.DigerBilgilerSayfasi.DriveUniteleriSayfasi.DriveUniteModel

class DriveModel {

    var driveTagNo = ""
    var driveGuc = ""
    var driveAkim = ""
    var driveVoltaj = ""
    var driveModul = ""
    var driveCode = ""
    var driveSeriNo = ""
    var driveIGBT = ""
    var driveKondansator = ""
    var driveThickFirm = ""
    var driveAnaTetikleme = ""
    var driveIGBTtetikleme = ""
    var driveTetiklemeBesPCB = ""
    var driveNintBesPCB = ""
    var drive24vBes = ""
    var driveKontUnit = ""
    var driveFan = ""
    var driveSigorta1 = ""
    var driveSigorta2 = ""
    var driveDegTarihi = ""
    var driveMotorDegTarihi = ""

    constructor(
        driveTagNo: String,
        driveGuc: String,
        driveAkim: String,
        driveVoltaj: String,
        driveModul: String,
        driveCode: String,
        driveSeriNo: String,
        driveIGBT: String,
        driveKondansator: String,
        driveThickFirm: String,
        driveAnaTetikleme: String,
        driveIGBTtetikleme: String,
        driveTetiklemeBesPCB: String,
        driveNintBesPCB: String,
        drive24vBes: String,
        driveKontUnit: String,
        driveFan: String,
        driveSigorta1: String,
        driveSigorta2: String,
        driveDegTarihi: String,
        driveMotorDegTarihi : String
    ) {
        this.driveTagNo = driveTagNo
        this.driveGuc = driveGuc
        this.driveAkim = driveAkim
        this.driveVoltaj = driveVoltaj
        this.driveModul = driveModul
        this.driveCode = driveCode
        this.driveSeriNo = driveSeriNo
        this.driveIGBT = driveIGBT
        this.driveKondansator = driveKondansator
        this.driveThickFirm = driveThickFirm
        this.driveAnaTetikleme = driveAnaTetikleme
        this.driveIGBTtetikleme = driveIGBTtetikleme
        this.driveTetiklemeBesPCB = driveTetiklemeBesPCB
        this.driveNintBesPCB = driveNintBesPCB
        this.drive24vBes = drive24vBes
        this.driveKontUnit = driveKontUnit
        this.driveFan = driveFan
        this.driveSigorta1 = driveSigorta1
        this.driveSigorta2 = driveSigorta2
        this.driveDegTarihi = driveDegTarihi
        this.driveMotorDegTarihi = driveMotorDegTarihi
    }

    constructor(driveTagNo: String, driveGuc: String, driveSeriNo: String, driveDegTarihi: String) {
        this.driveTagNo = driveTagNo
        this.driveGuc = driveGuc
        this.driveSeriNo = driveSeriNo
        this.driveDegTarihi = driveDegTarihi
    }

    constructor()


}