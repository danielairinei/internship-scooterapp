package com.internship.move.presentation.menu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentMenuChangePasswordBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class MenuChangePasswordFragment : Fragment(R.layout.fragment_menu_change_password) {

    private val binding by viewBinding(FragmentMenuChangePasswordBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(MenuChangePasswordFragmentDirections.actionMenuChangePasswordFragmentToMenuFragment())
        }

        binding.oldPasswordTIET.doOnTextChanged { _, _, _, _ ->
            changeBtnState(
                binding.oldPasswordTIET.text?.isNotEmpty() ?: false,
                binding.newPasswordTIET.text?.isNotEmpty() ?: false,
                binding.confirmPasswordTIET.text?.isNotEmpty() ?: false
            )
        }

        binding.newPasswordTIET.doOnTextChanged { _, _, _, _ ->
            changeBtnState(
                binding.oldPasswordTIET.text?.isNotEmpty() ?: false,
                binding.newPasswordTIET.text?.isNotEmpty() ?: false,
                binding.confirmPasswordTIET.text?.isNotEmpty() ?: false
            )
        }

        binding.confirmPasswordTIET.doOnTextChanged { _, _, _, _ ->
            changeBtnState(
                binding.oldPasswordTIET.text?.isNotEmpty() ?: false,
                binding.newPasswordTIET.text?.isNotEmpty() ?: false,
                binding.confirmPasswordTIET.text?.isNotEmpty() ?: false
            )
        }
        binding.saveEditsBtn.setOnClickListener{
            Toast.makeText(requireContext(),"Password changed!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeBtnState(oldPasswordIsNotEmpty: Boolean, newPasswordIsNotEmpty: Boolean, confirmPasswordIsNotEmpty: Boolean) {
        binding.saveEditsBtn.isEnabled = oldPasswordIsNotEmpty && newPasswordIsNotEmpty && confirmPasswordIsNotEmpty
    }
}