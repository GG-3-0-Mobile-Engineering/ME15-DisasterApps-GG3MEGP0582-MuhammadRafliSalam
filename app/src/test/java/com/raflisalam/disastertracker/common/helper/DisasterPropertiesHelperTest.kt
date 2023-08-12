package com.raflisalam.disastertracker.common.helper

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
            "Gempa bumi, getaran mendalam yang terjadi di permukaan bumi akibat pergeseran lempeng tektonik. Energi yang dilepaskan menyebabkan goncangan hebat, merusak bangunan dan infrastruktur, serta mengancam keselamatan jiwa.",
            DisasterPropertiesHelper.getDisasterDesc("earthquake")
        )
        assertEquals(
            "Kebakaran, wabah api yang tak terkendali, dapat melahap bangunan, hutan, atau lahan. Meninggalkan kerugian material dan lingkungan yang besar, serta mengancam nyawa dan sumber daya alam.",
            DisasterPropertiesHelper.getDisasterDesc("fire")
        )
        assertEquals(
            "Banjir, luapan air yang melanda daratan akibat curah hujan yang tinggi atau luapan sungai. Mengakibatkan genangan air, merendam pemukiman, pertanian, dan infrastruktur, serta menimbulkan kerugian ekonomi dan dampak kesehatan.",
            DisasterPropertiesHelper.getDisasterDesc("flood")
        )
        assertEquals(
            "Kabut, kondensasi uap air yang menyebabkan penglihatan terbatas dan udara terasa lembab. Dalam kasus yang ekstrem, kabut tebal dapat menyebabkan gangguan lalu lintas dan masalah kesehatan pernapasan bagi masyarakat",
            DisasterPropertiesHelper.getDisasterDesc("haze")
        )
        assertEquals(
            "Gunung meletus, letusan material vulkanik dari kawah gunung berapi. Mengeluarkan asap, lava, dan awan panas, yang bisa menyebabkan kerusakan besar pada lingkungan dan mengancam keselamatan warga di sekitar gunung.",
            DisasterPropertiesHelper.getDisasterDesc("volcano")
        )
        assertEquals(
            "Badai angin, cuaca ekstrem dengan angin kencang dan hujan deras. Merusak atap rumah, pohon tumbang, dan mengganggu distribusi listrik, serta mengancam keselamatan warga di luar ruangan.",
            DisasterPropertiesHelper.getDisasterDesc("wind")
        )
        assertEquals(
            "Disaster Not Found",
            DisasterPropertiesHelper.getDisasterTitle("")
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
            "Bencana Banjir",
            DisasterPropertiesHelper.getDisasterTitle("flood")
        )
        assertEquals(
            "Kabut",
            DisasterPropertiesHelper.getDisasterTitle("haze")
        )
        assertEquals(
            "Gunung Meletus",
            DisasterPropertiesHelper.getDisasterTitle("volcano")
        )
        assertEquals(
            "Badai Angin",
            DisasterPropertiesHelper.getDisasterTitle("wind")
        )
        assertEquals(
            "Disaster Not Found",
            DisasterPropertiesHelper.getDisasterTitle("")
        )
    }
}