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

/*
    fun adminAreaToRegionCode(cityName: String?): String {
        return when (cityName) {
            "Aceh" -> DisasterArea.ACEH.value.code
            "Bali" -> DisasterArea.BALI.value.code
            "Kepulauan Bangka Belitung" -> DisasterArea.KEPULAUAN_BANGKA_BELITUNG.value.code
            "Banten" -> DisasterArea.BANTEN.value.code
            "Bengkulu" -> DisasterArea.BENGKULU.value.code
            "Jawa Tengah" -> DisasterArea.JAWA_TENGAH.value.code
            "Kalimantan Tengah" -> DisasterArea.KALIMANTAN_TENGAH.value.code
            "Central Sulawesi" -> DisasterArea.SULAWESI_TENGAH.value.code
            "Jawa Timur" -> DisasterArea.JAWA_TIMUR.value.code
            "East Kalimantan" -> DisasterArea.KALIMANTAN_TIMUR.value.code
            "East Nusa Tenggara" -> DisasterArea.NUSA_TENGGARA_TIMUR.value.code
            "Gorontalo" -> DisasterArea.GORONTALO.value.code
            "Daerah Khusus Ibukota Jakarta" -> DisasterArea.DKI_JAKARTA.value.code
            "Jambi" -> DisasterArea.JAMBI.value.code
            "Lampung" -> DisasterArea.LAMPUNG.value.code
            "Maluku" -> DisasterArea.MALUKU.value.code
            "North Kalimantan" -> DisasterArea.KALIMANTAN_UTARA.value.code
            "North Maluku" -> DisasterArea.MALUKU_UTARA.value.code
            "Sulawesi Utara" -> DisasterArea.SULAWESI_UTARA.value.code
            "Sumatera Utara" -> DisasterArea.SUMATERA_UTARA.value.code
            "Papua" -> DisasterArea.PAPUA.value.code
            "Riau" -> DisasterArea.RIAU.value.code
            "Kepulauan Riau" -> DisasterArea.KEPULAUAN_RIAU.value.code
            "South East Sulawesi" -> DisasterArea.SULAWESI_TENGGARA.value.code
            "South Kalimantan" -> DisasterArea.KALIMANTAN_SELATAN.value.code
            "South Sulawesi" -> DisasterArea.SULAWESI_SELATAN.value.code
            "South Sumatera" -> DisasterArea.SUMATERA_SELATAN.value.code
            "Daerah Istimewa Yogyakarta" -> DisasterArea.DI_YOGYAKARTA.value.code
            "Jawa Barat" -> DisasterArea.JAWA_BARAT.value.code
            "Kalimantan Barat" -> DisasterArea.KALIMANTAN_BARAT.value.code
            "West Nusa Tenggara" -> DisasterArea.NUSA_TENGGARA_BARAT.value.code
            "West Papua" -> DisasterArea.PAPUA_BARAT.value.code
            "West Sulawesi" -> DisasterArea.SULAWESI_BARAT.value.code
            "Sumatera Barat" -> DisasterArea.SUMATERA_BARAT.value.code
            else -> "invalid region code"
        }
    }
*/
}