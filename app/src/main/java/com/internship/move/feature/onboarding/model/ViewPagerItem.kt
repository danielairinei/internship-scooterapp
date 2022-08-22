package com.internship.move.feature.onboarding.model

data class ViewPagerItem(
    val id: Int,
    val photoUrl : Int,
    val heading: Int,
    val content: Int,
    val isLastItem : Boolean = false
)