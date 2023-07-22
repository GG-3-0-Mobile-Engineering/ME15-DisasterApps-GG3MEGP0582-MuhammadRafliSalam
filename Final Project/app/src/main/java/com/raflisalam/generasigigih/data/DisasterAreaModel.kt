package com.raflisalam.generasigigih.data

data class DisasterAreaModel(
    val name: String,
    val code: String
)

enum class DisasterArea(val value: DisasterAreaModel) {
    ACEH(DisasterAreaModel("Aceh", "ID-AC")),
    BALI(DisasterAreaModel("Bali", "ID-BA")),
    KEPULAUAN_BANGKA_BELITUNG(DisasterAreaModel("Kep. Bangka Belitung", "ID-BB")),
    BANTEN(DisasterAreaModel("Banten", "ID-BT")),
    BENGKULU(DisasterAreaModel("Bengkulu", "ID-BE")),
    JAWA_TENGAH(DisasterAreaModel("Jawa Tengah", "ID-JT")),
    KALIMANTAN_TENGAH(DisasterAreaModel("Kalimantan Tengah", "ID-KT")),
    SULAWESI_TENGAH(DisasterAreaModel("Sulawesi Tengah", "ID-ST")),
    JAWA_TIMUR(DisasterAreaModel("Jawa Timur", "ID-JI")),
    KALIMANTAN_TIMUR(DisasterAreaModel("Kalimantan Timur", "ID-KI")),
    NUSA_TENGGARA_TIMUR(DisasterAreaModel("Nusa Tenggara Timur", "ID-NT")),
    GORONTALO(DisasterAreaModel("Gorontalo", "ID-GO")),
    DKI_JAKARTA(DisasterAreaModel("DKI Jakarta", "ID-JK")),
    JAMBI(DisasterAreaModel("Jambi","ID-JA")),
    LAMPUNG(DisasterAreaModel("Lampung","ID-LA")),
    MALUKU(DisasterAreaModel("Maluku","ID-MA")),
    KALIMANTAN_UTARA(DisasterAreaModel("Kalimantan Utara","ID-KU")),
    MALUKU_UTARA(DisasterAreaModel("Maluku Utara","ID-MU")),
    SULAWESI_UTARA(DisasterAreaModel("Sulawesi Utara","ID-SA")),
    SUMATERA_UTARA(DisasterAreaModel("Sumatera Utara","ID-SU")),
    PAPUA(DisasterAreaModel("Papua","ID-PA")),
    RIAU(DisasterAreaModel("Riau", "ID-RI")),
    KEPULAUAN_RIAU(DisasterAreaModel("Kepulauan Riau","ID-KR")),
    SULAWESI_TENGGARA(DisasterAreaModel("Sulawesi Tenggara", "ID-SG")),
    KALIMANTAN_SELATAN(DisasterAreaModel("Kalimantan Selatan","ID-KS")),
    SULAWESI_SELATAN(DisasterAreaModel("Sulawesi Selatan","ID-SN")),
    SUMATERA_SELATAN(DisasterAreaModel("Sumatera Selatan","ID-SS")),
    DI_YOGYAKARTA(DisasterAreaModel("DI Yogyakarta","ID-YO")),
    JAWA_BARAT(DisasterAreaModel("Jawa Barat","ID-JB")),
    KALIMANTAN_BARAT(DisasterAreaModel("Kalimantan Barat", "ID-KB")),
    NUSA_TENGGARA_BARAT(DisasterAreaModel("Nusa Tenggara Barat", "ID-NB")),
    PAPUA_BARAT(DisasterAreaModel("Papua Barat", "ID-PB")),
    SULAWESI_BARAT(DisasterAreaModel("Sulawesi Barat", "ID-SR")),
    SUMATERA_BARAT(DisasterAreaModel("Sumatera Barat", "ID-SB"))
}