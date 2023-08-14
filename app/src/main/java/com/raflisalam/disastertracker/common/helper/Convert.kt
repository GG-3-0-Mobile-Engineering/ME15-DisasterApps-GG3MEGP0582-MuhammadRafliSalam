package com.raflisalam.disastertracker.common.helper

import com.raflisalam.disastertracker.data.local.DisasterArea

object Convert {

    fun regionNameToRegionCode(regionName: String): String {
        return when (regionName) {
            "aceh" -> DisasterArea.ACEH.value.code
            "bali" -> DisasterArea.BALI.value.code
            "kepulauan bangka belitung" -> DisasterArea.KEPULAUAN_BANGKA_BELITUNG.value.code
            "banten" -> DisasterArea.BANTEN.value.code
            "bengkulu" -> DisasterArea.BENGKULU.value.code
            "jawa tengah" -> DisasterArea.JAWA_TENGAH.value.code
            "kalimantan tengah" -> DisasterArea.KALIMANTAN_TENGAH.value.code
            "sulawesi tengah" -> DisasterArea.SULAWESI_TENGAH.value.code
            "jawa timur" -> DisasterArea.JAWA_TIMUR.value.code
            "kalimantan timur" -> DisasterArea.KALIMANTAN_TIMUR.value.code
            "nusa tenggara timur" -> DisasterArea.NUSA_TENGGARA_TIMUR.value.code
            "gorontalo" -> DisasterArea.GORONTALO.value.code
            "dki jakarta" -> DisasterArea.DKI_JAKARTA.value.code
            "jambi" -> DisasterArea.JAMBI.value.code
            "lampung" -> DisasterArea.LAMPUNG.value.code
            "maluku" -> DisasterArea.MALUKU.value.code
            "kalimantan utara" -> DisasterArea.KALIMANTAN_UTARA.value.code
            "maluku utara" -> DisasterArea.MALUKU_UTARA.value.code
            "sulawesi utara" -> DisasterArea.SULAWESI_UTARA.value.code
            "sumatera utara" -> DisasterArea.SUMATERA_UTARA.value.code
            "papua" -> DisasterArea.PAPUA.value.code
            "riau" -> DisasterArea.RIAU.value.code
            "kepulauan riau" -> DisasterArea.KEPULAUAN_RIAU.value.code
            "sulawesi tenggara" -> DisasterArea.SULAWESI_TENGGARA.value.code
            "kalimantan selatan" -> DisasterArea.KALIMANTAN_SELATAN.value.code
            "sulawesi selatan" -> DisasterArea.SULAWESI_SELATAN.value.code
            "sumatera selatan" -> DisasterArea.SUMATERA_SELATAN.value.code
            "di yogyakarta" -> DisasterArea.DI_YOGYAKARTA.value.code
            "jawa barat" -> DisasterArea.JAWA_BARAT.value.code
            "kalimantan barat" -> DisasterArea.KALIMANTAN_BARAT.value.code
            "nusa tenggara barat" -> DisasterArea.NUSA_TENGGARA_BARAT.value.code
            "papua barat" -> DisasterArea.PAPUA_BARAT.value.code
            "sulawesi barat" -> DisasterArea.SULAWESI_BARAT.value.code
            "sumatera barat" -> DisasterArea.SUMATERA_BARAT.value.code
            else -> "invalid region name"
        }
    }

    fun regionCodeToRegionName(regionCode: String): String {
        return when (regionCode) {
            "ID-AC" -> DisasterArea.ACEH.value.name
            "ID-BA" -> DisasterArea.BALI.value.name
            "ID-BB" -> DisasterArea.KEPULAUAN_BANGKA_BELITUNG.value.name
            "ID-BT" -> DisasterArea.BANTEN.value.name
            "ID-BE" -> DisasterArea.BENGKULU.value.name
            "ID-JT" -> DisasterArea.JAWA_TENGAH.value.name
            "ID-KT" -> DisasterArea.KALIMANTAN_TENGAH.value.name
            "ID-ST" -> DisasterArea.SULAWESI_TENGAH.value.name
            "ID-JI" -> DisasterArea.JAWA_TIMUR.value.name
            "ID-KI" -> DisasterArea.KALIMANTAN_TIMUR.value.name
            "ID-NT" -> DisasterArea.NUSA_TENGGARA_TIMUR.value.name
            "ID-GO" -> DisasterArea.GORONTALO.value.name
            "ID-JK" -> DisasterArea.DKI_JAKARTA.value.name
            "ID-JA" -> DisasterArea.JAMBI.value.name
            "ID-LA" -> DisasterArea.LAMPUNG.value.name
            "ID-MA" -> DisasterArea.MALUKU.value.name
            "ID-KU" -> DisasterArea.KALIMANTAN_UTARA.value.name
            "ID-MU" -> DisasterArea.MALUKU_UTARA.value.name
            "ID-SA" -> DisasterArea.SULAWESI_UTARA.value.name
            "ID-SU" -> DisasterArea.SUMATERA_UTARA.value.name
            "ID-PA" -> DisasterArea.PAPUA.value.name
            "ID-RI" -> DisasterArea.RIAU.value.name
            "ID-KR" -> DisasterArea.KEPULAUAN_RIAU.value.name
            "ID-SG" -> DisasterArea.SULAWESI_TENGGARA.value.name
            "ID-KS" -> DisasterArea.KALIMANTAN_SELATAN.value.name
            "ID-SN" -> DisasterArea.SULAWESI_SELATAN.value.name
            "ID-SS" -> DisasterArea.SUMATERA_SELATAN.value.name
            "ID-YO" -> DisasterArea.DI_YOGYAKARTA.value.name
            "ID-JB" -> DisasterArea.JAWA_BARAT.value.name
            "ID-KB" -> DisasterArea.KALIMANTAN_BARAT.value.name
            "ID-NB" -> DisasterArea.NUSA_TENGGARA_BARAT.value.name
            "ID-PB" -> DisasterArea.PAPUA_BARAT.value.name
            "ID-SR" -> DisasterArea.SULAWESI_BARAT.value.name
            "ID-SB" -> DisasterArea.SUMATERA_BARAT.value.name
            else -> "Lokasi tidak diketahui"
        }
    }

    fun chipValuesToEnglish(selectedChip: String) : String {
        return when (selectedChip) {
            "Banjir" -> "flood"
            "Badai" -> "wind"
            "Gempa" -> "earthquake"
            "Kabut" -> "haze"
            "Kebakaran" -> "fire"
            "Gunung Berapi" -> "volcano"
            else -> "Unknown chip"
        }
    }

}