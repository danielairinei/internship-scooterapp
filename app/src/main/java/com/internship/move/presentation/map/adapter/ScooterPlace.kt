package com.internship.move.presentation.map.adapter

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class ScooterPlace(
    val latLng: LatLng,
    val name: String,
    val id: String
) : ClusterItem {

    override fun getPosition(): LatLng = latLng

    override fun getTitle(): String = name

    override fun getSnippet(): String = id
}