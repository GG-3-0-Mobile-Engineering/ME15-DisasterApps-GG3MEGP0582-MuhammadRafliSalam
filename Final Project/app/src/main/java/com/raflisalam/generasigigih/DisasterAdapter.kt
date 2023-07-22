package com.raflisalam.generasigigih

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.raflisalam.generasigigih.data.DisasterImages
import com.raflisalam.generasigigih.databinding.ListDisasterBinding

class DisasterAdapter(private var disasters: List<DisasterReports>) : RecyclerView.Adapter<DisasterAdapter.DisasterViewHolder>() {


    inner class DisasterViewHolder(val binding: ListDisasterBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisasterViewHolder {
        val binding = ListDisasterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DisasterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return disasters.size
    }

    override fun onBindViewHolder(holder: DisasterViewHolder, position: Int) {
        with(holder) {
            val data = disasters[position]
            binding.apply {
                val imageUrl = data.imageUrl ?: getDisasterImage(data.disasterType)
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .apply(RequestOptions())
                    .into(image)
                title.text = getDisasterTitle(data.disasterType)
                text.text = data.text
                source.text = "by ${data.source} ${data.createdAt}"
            }
        }
    }

    private fun getDisasterImage(disasterType: String): String {
        return when (disasterType) {
            "earthquake" -> DisasterImages.EARTHQUAKE.values.imageUrl
            "fire" -> DisasterImages.FIRE.values.imageUrl
            "flood" -> DisasterImages.FLOOD.values.imageUrl
            "haze" -> DisasterImages.HAZE.values.imageUrl
            "volcano" -> DisasterImages.VOLCANO.values.imageUrl
            else -> DisasterImages.WIND.values.imageUrl
        }
    }

    private fun getDisasterTitle(disasterType: String): String {
        return when (disasterType) {
            "earthquake" -> DisasterImages.EARTHQUAKE.values.name
            "fire" -> DisasterImages.FIRE.values.name
            "flood" -> DisasterImages.FLOOD.values.name
            "haze" -> DisasterImages.HAZE.values.name
            "volcano" -> DisasterImages.VOLCANO.values.name
            "wind" -> DisasterImages.WIND.values.name
            else -> "Disaster Not Found"
        }
    }
}