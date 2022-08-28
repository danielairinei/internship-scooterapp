package com.internship.move.util

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.TextView

fun TextView.makeLinks(text: String, action: (() -> Unit)? = null) {
    val spannableString = SpannableString(this.text)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(view: View) {
            action?.invoke()
        }

        override fun updateDrawState(drawState: TextPaint) {
            super.updateDrawState(drawState)
            drawState.isUnderlineText = true
            drawState.color = currentTextColor
        }
    }
    val index = this.text.toString().indexOf(text, 0)
    spannableString.setSpan(clickableSpan, index, index + text.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

    setText(spannableString, TextView.BufferType.SPANNABLE)
    movementMethod = LinkMovementMethod.getInstance()
    highlightColor = Color.TRANSPARENT
}


fun Button.enableButton() {
    if (this.isEnabled) {
        this.alpha = 1F
    } else {
        this.alpha = 0.3F
    }
}