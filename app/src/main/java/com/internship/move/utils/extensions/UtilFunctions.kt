package com.internship.move.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.internship.move.R
import com.internship.move.data.dto.ErrorResponse
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

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? =
    ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }

fun getPhotoByBattery(percentage: Int?): Int = when (percentage) {
    in 76..100 -> R.drawable.ic_battery_100
    in 51..75 -> R.drawable.ic_battery_75
    in 26..50 -> R.drawable.ic_battery_50
    in 1..25 -> R.drawable.ic_battery_25
    else -> R.drawable.ic_battery_0
}
