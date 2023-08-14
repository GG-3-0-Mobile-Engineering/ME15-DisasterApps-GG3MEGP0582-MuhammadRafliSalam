package com.raflisalam.disastertracker.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.raflisalam.disastertracker.common.helper.Convert
import com.raflisalam.disastertracker.common.helper.DisasterPropertiesHelper
import com.raflisalam.disastertracker.databinding.ListDisasterHomeBinding
import com.raflisalam.disastertracker.domain.model.DisasterReports

class DiscoverAdapter (private var listDisasterReports: List<DisasterReports>?): RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder>() {

    inner class DiscoverViewHolder(val binding: ListDisasterHomeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
        val binding = ListDisasterHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiscoverViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDisasterReports?.size ?: DEFAULT_LIST
    }

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        with(holder) {
            val data = listDisasterReports?.get(position)
            if (data != null) {
                binding.apply {
                    val imageUrl = data.imageUrl ?: DisasterPropertiesHelper.getDisasterImage(data.disasterType)
                    Glide.with(itemView.context)
                        .load(imageUrl)
                        .apply(RequestOptions())
                        .into(image)
                    title.text = data.title
                    location.text = Convert.regionCodeToRegionName(data.tags.regionCode)
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_LIST = 0
    }
}