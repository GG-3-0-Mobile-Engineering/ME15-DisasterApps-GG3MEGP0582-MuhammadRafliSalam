package com.raflisalam.generasigigih.data

data class DisasterImageModel(
    val name: String,
    val imageUrl: String
)

enum class DisasterImages(val values: DisasterImageModel) {
    FLOOD(DisasterImageModel("Banjir", "https://akcdn.detik.net.id/visual/2021/02/19/banjir-cipinang-melayu-jakarta-timur-3_169.jpeg?w=650")),
    FIRE(DisasterImageModel("Kebakaran Hutan", "https://images.theconversation.com/files/440587/original/file-20220113-17-n8jcpe.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=1356&h=668&fit=crop")),
    EARTHQUAKE(DisasterImageModel("Gempa Bumi", "https://cdn0-production-images-kly.akamaized.net/5McXCOftlQTwJzBa6tJ99dix5hg=/1200x675/smart/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/4237057/original/054916400_1669202311-20221123-Gempa-Cianjur-Longsor-Cijendil-Herman-11.jpg")),
    HAZE(DisasterImageModel("Kabut", "https://mmc.tirto.id/image/otf/500x0/2019/09/15/dampak-karhutla-di-kalsel-antarafoto_ratio-16x9.jpg")),
    WIND(DisasterImageModel("Badai Angin", "https://bolaskor.com/media/8a/69/d8/8a69d87cfe04ff076ce105af2a0403d9_754x.jpg")),
    VOLCANO(DisasterImageModel("Gunung Berapi", "https://akcdn.detik.net.id/visual/2021/01/27/merapi-erupsi_169.jpeg?w=650"))
}