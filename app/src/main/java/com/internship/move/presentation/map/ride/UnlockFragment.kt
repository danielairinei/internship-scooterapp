package com.internship.move.presentation.map.ride

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.internship.move.R
import com.internship.move.databinding.FragmentUnlockBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class UnlockFragment : Fragment(R.layout.fragment_unlock) {

    private val binding by viewBinding(FragmentUnlockBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {

        }
    }
}