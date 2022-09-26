package com.internship.move.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentMenuHistoryBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class MenuHistoryFragment : Fragment(R.layout.fragment_menu_history) {

    private val binding by viewBinding(FragmentMenuHistoryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(MenuHistoryFragmentDirections.actionMenuHistoryFragmentToMenuFragment())
        }
    }
}