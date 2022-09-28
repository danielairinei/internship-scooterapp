package com.internship.move.utils.extensions

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.internship.move.data.dto.user.ErrorResponse
import com.squareup.moshi.JsonAdapter
import retrofit2.HttpException

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

fun Exception.toErrorResponse(errorResponseDtoJsonAdapter: JsonAdapter<ErrorResponse>): ErrorResponse =
    if (this is HttpException) {
        errorResponseDtoJsonAdapter.fromJson(response()?.errorBody()?.string().toString())
            ?: ErrorResponse(message.toString())
    } else {
        ErrorResponse(message.toString())
    }