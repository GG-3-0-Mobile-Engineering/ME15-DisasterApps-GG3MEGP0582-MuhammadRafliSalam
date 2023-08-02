package com.raflisalam.disastertracker.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.raflisalam.disastertracker.databinding.ListDisasterBinding
import com.raflisalam.disastertracker.domain.model.DisasterReports

class DisasterAdapter(private var listDisasterReports: List<DisasterReports>?) : RecyclerView.Adapter<DisasterAdapter.DisasterViewHolder>() {

    inner class DisasterViewHolder (val binding: ListDisasterBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisasterAdapter.DisasterViewHolder {
        val binding = ListDisasterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DisasterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DisasterAdapter.DisasterViewHolder, position: Int) {
        with(holder) {
            val data = listDisasterReports?.get(position)
            if (data != null) {
                binding.apply {
                    val imageUrl = data.imageUrl
                    Glide.with(itemView.context)
                        .load(imageUrl)
                        .apply(RequestOptions())
                        .into(image)
                    title.text = data.title
                    description.text = data.description
                    source.text = "by ${data.createdAt}"
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listDisasterReports?.size ?: DEFAULT_LIST
    }

    companion object {
        private const val DEFAULT_LIST = 0
    }
}