package com.example.wonhoi_seoul_subway_info_app.presentation.stations

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.wonhoi_seoul_subway_info_app.databinding.ItemStationBinding
import com.example.wonhoi_seoul_subway_info_app.domain.Station
import com.example.wonhoi_seoul_subway_info_app.extension.dip
import com.example.wonhoi_seoul_subway_info_app.presentation.view.Badge

class StationsAdapter : RecyclerView.Adapter<StationsAdapter.ViewHolder>() {

    var data : List<Station> = emptyList()

    var onItemClickListener : ((Station) -> Unit)? = null
    var onFavoriteClickListener : ((Station) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding : ItemStationBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(data[adapterPosition])
            }

            binding.favorite.setOnClickListener {
                onFavoriteClickListener?.invoke(data[adapterPosition])
            }
        }

        fun bind(station : Station) {
            binding.badgeContainer.removeAllViews()

            binding.stationNameTextView.text = station.name
            binding.favorite.isChecked = station.isFavorited


            // 환승역도 표시해줌
            station.connectedSubway
                .forEach { subway ->
                    binding.badgeContainer.addView(
                        Badge(binding.root.context).apply {
                            badgeColor = subway.color
                            text = subway.label
                            layoutParams =
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                ).apply {
                                    rightMargin = dip(6f)
                                }
                        }
                    )
                }

        }

    }



}