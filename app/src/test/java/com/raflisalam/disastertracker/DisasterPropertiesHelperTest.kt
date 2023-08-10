package com.raflisalam.disastertracker

import com.raflisalam.disastertracker.common.helper.DisasterPropertiesHelper
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DisasterPropertiesHelperTest {

    @Test
    fun testGetDisasterImage() {
        assertEquals(
            "https://cdn0-production-images-kly.akamaized.net/5McXCOftlQTwJzBa6tJ99dix5hg=/1200x675/smart/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/4237057/original/054916400_1669202311-20221123-Gempa-Cianjur-Longsor-Cijendil-Herman-11.jpg",
            DisasterPropertiesHelper.getDisasterImage("earthquake")
        )
        assertEquals(
            "https://images.theconversation.com/files/440587/original/file-20220113-17-n8jcpe.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=1356&h=668&fit=crop",
            DisasterPropertiesHelper.getDisasterImage("fire")
        )
        assertEquals(
            "https://akcdn.detik.net.id/visual/2021/02/19/banjir-cipinang-melayu-jakarta-timur-3_169.jpeg?w=650",
            DisasterPropertiesHelper.getDisasterImage("flood")
        )
        assertEquals(
            "https://mmc.tirto.id/image/otf/500x0/2019/09/15/dampak-karhutla-di-kalsel-antarafoto_ratio-16x9.jpg",
            DisasterPropertiesHelper.getDisasterImage("haze")
        )
        assertEquals(
            "https://akcdn.detik.net.id/visual/2021/01/27/merapi-erupsi_169.jpeg?w=650",
            DisasterPropertiesHelper.getDisasterImage("volcano")
        )
        assertEquals(
            "https://bolaskor.com/media/8a/69/d8/8a69d87cfe04ff076ce105af2a0403d9_754x.jpg",
            DisasterPropertiesHelper.getDisasterImage("wind")
        )
    }

    @Test
    fun testGetDisasterDesc() {
        assertEquals(
            "Deskripsi Gempa Bumi",
            DisasterPropertiesHelper.getDisasterDesc("earthquake")
        )
        assertEquals(
            "Deskripsi Kebakaran",
            DisasterPropertiesHelper.getDisasterDesc("fire")
        )
        assertEquals(
            "Deskripsi Banjir",
            DisasterPropertiesHelper.getDisasterDesc("flood")
        )
        assertEquals(
            "Deskripsi Asap",
            DisasterPropertiesHelper.getDisasterDesc("haze")
        )
        assertEquals(
            "Deskripsi Gunung Meletus",
            DisasterPropertiesHelper.getDisasterDesc("volcano")
        )
        assertEquals(
            "Deskripsi Angin Kencang",
            DisasterPropertiesHelper.getDisasterDesc("wind")
        )
        assertEquals(
            "Bencana Tidak Ditemukan",
            DisasterPropertiesHelper.getDisasterDesc("tornado")
        )
    }

    @Test
    fun testGetDisasterTitle() {
        assertEquals(
            "Gempa Bumi",
            DisasterPropertiesHelper.getDisasterTitle("earthquake")
        )
        assertEquals(
            "Kebakaran",
            DisasterPropertiesHelper.getDisasterTitle("fire")
        )
        assertEquals(
            "Banjir",
            DisasterPropertiesHelper.getDisasterTitle("flood")
        )
        assertEquals(
            "Asap",
            DisasterPropertiesHelper.getDisasterTitle("haze")
        )
        assertEquals(
            "Gunung Meletus",
            DisasterPropertiesHelper.getDisasterTitle("volcano")
        )
        assertEquals(
            "Angin Kencang",
            DisasterPropertiesHelper.getDisasterTitle("wind")
        )
        assertEquals(
            "Bencana Tidak Ditemukan",
            DisasterPropertiesHelper.getDisasterTitle("tornado")
        )
    }
}