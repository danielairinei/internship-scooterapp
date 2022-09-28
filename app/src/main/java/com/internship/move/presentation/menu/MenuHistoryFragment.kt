package com.internship.move.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.internship.move.R
import com.internship.move.databinding.FragmentMenuHistoryBinding
import com.internship.move.presentation.menu.adapter.RideHistoryAdapter
import com.internship.move.presentation.menu.adapter.RideHistoryItem
import com.internship.move.presentation.menu.adapter.RideHistoryItemDecorator
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class MenuHistoryFragment : Fragment(R.layout.fragment_menu_history) {

    private val binding by viewBinding(FragmentMenuHistoryBinding::bind)
    private val recyclerAdapter by lazy { RideHistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(MenuHistoryFragmentDirections.actionMenuHistoryFragmentToMenuFragment())
        }

        binding.historyRV.adapter = recyclerAdapter
        binding.historyRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.historyRV.addItemDecoration(RideHistoryItemDecorator(resources.getDimension(R.dimen.history_card_view_spacing)))

        recyclerAdapter.submitList(getRides())
    }

    private fun getRides(): List<RideHistoryItem> = (1..10).map { index -> RideHistoryItem(index) }
}