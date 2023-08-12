package com.raflisalam.disastertracker.common.helper

import com.raflisalam.disastertracker.common.helper.Convert.chipValuesToEnglish
import com.raflisalam.disastertracker.common.helper.Convert.regionNameToRegionCode
import com.raflisalam.disastertracker.data.local.DisasterArea
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ConvertHelperTest {

    @Test
    fun `regionNameToRegionCode should return correct region code for valid region name`() {
        val regionName = "aceh"
        val expectedRegionCode = DisasterArea.ACEH.value.code

        val result = regionNameToRegionCode(regionName)

        assertEquals(expectedRegionCode, result)
    }

    @Test
    fun `regionNameToRegionCode should return invalid region name for unknown region name`() {
        val regionName = "unknown_region"
        val expectedRegionCode = "invalid region name"

        val result = regionNameToRegionCode(regionName)

        assertEquals(expectedRegionCode, result)
    }

    @Test
    fun `chipValuesToEnglish should return correct English value for valid chip value`() {
        val selectedChip = "Banjir"
        val expectedEnglishValue = "flood"

        val result = chipValuesToEnglish(selectedChip)

        assertEquals(expectedEnglishValue, result)
    }

    @Test
    fun `chipValuesToEnglish should return Unknown chip for unknown chip value`() {
        val selectedChip = "Unknown Chip"
        val expectedEnglishValue = "Unknown chip"

        val result = chipValuesToEnglish(selectedChip)

        assertEquals(expectedEnglishValue, result)
    }
}
