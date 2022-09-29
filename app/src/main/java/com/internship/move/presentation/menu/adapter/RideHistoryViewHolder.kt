package com.internship.move.presentation.menu.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.internship.move.R
import com.internship.move.databinding.ItemHistoryCardViewBinding

class RideHistoryViewHolder(private val binding: ItemHistoryCardViewBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(rideHistoryItem: RideHistoryItem) {
        binding.startLocationTV.text = rideHistoryItem.startAddress
        binding.endLocationTV.text = rideHistoryItem.endAddress
        binding.travelTimeTV.text = rideHistoryItem.time
        binding.distanceTV.text = itemView.resources.getString(R.string.history_card_view_distance, rideHistoryItem.distance)
    }
}