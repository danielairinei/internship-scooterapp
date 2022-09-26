package com.internship.move.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.internship.move.databinding.ItemHistoryCardViewBinding

class RideHistoryAdapter : ListAdapter<RideHistoryItem, RideHistoryViewHolder>(RideHistoryItemDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideHistoryViewHolder {
        val binding = ItemHistoryCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RideHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RideHistoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size
}