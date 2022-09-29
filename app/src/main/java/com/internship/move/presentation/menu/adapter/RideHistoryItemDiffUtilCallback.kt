package com.internship.move.presentation.menu.adapter

import androidx.recyclerview.widget.DiffUtil

class RideHistoryItemDiffUtilCallback : DiffUtil.ItemCallback<RideHistoryItem>() {

    override fun areContentsTheSame(oldItem: RideHistoryItem, newItem: RideHistoryItem): Boolean = newItem == oldItem

    override fun areItemsTheSame(oldItem: RideHistoryItem, newItem: RideHistoryItem): Boolean = newItem.rideId == oldItem.rideId
}