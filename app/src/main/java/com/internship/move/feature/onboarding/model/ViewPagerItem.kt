package com.internship.move.feature.onboarding.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class ViewPagerItem(
    @DrawableRes
    val photo: Int,
    @StringRes
    val heading: Int,
    @StringRes
    val content: Int,
    val isLastItem: Boolean = false
) : Parcelable