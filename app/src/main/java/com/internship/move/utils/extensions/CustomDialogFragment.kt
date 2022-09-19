package com.internship.move.utils.extensions

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.internship.move.R
import com.internship.move.databinding.ItemCustomDialogBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class CustomDialogFragment : DialogFragment(R.layout.item_custom_dialog) {

    private val binding by viewBinding(ItemCustomDialogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDialog()
        initListener()
    }

    private fun initDialog() {

        arguments?.let {
            binding.titleTV.text = it.getString(KEY_CUSTOM_DIALOG_TITLE)
            binding.contentTV.text = it.getString(KEY_CUSTOM_DIALOG_CONTENT)
            binding.dialogBtn.text = it.getString(KEY_CUSTOM_DIALOG_TEXT_BTN)
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun initListener() {
        binding.dialogBtn.setOnClickListener {
            dialog?.dismiss()
        }
    }

    companion object {
        private const val KEY_CUSTOM_DIALOG_TITLE = "KEY_CUSTOM_DIALOG_TITLE"
        private const val KEY_CUSTOM_DIALOG_CONTENT = "KEY_CUSTOM_DIALOG_CONTENT"
        private const val KEY_CUSTOM_DIALOG_TEXT_BTN = "KEY_CUSTOM_DIALOG_TEXT_BTN"

        private val bundle = Bundle()

        fun newInstance(title: String, content: String, textBtn: String): CustomDialogFragment = CustomDialogFragment().apply {
            bundle.putString(KEY_CUSTOM_DIALOG_TITLE, title)
            bundle.putString(KEY_CUSTOM_DIALOG_CONTENT, content)
            bundle.putString(KEY_CUSTOM_DIALOG_TEXT_BTN, textBtn)

            arguments = bundle
        }
    }
}