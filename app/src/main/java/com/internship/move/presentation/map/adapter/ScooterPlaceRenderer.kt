package com.internship.move.presentation.map.adapter

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.internship.move.R
import com.internship.move.utils.extensions.bitmapDescriptorFromVector

private const val KEY_MINIMUM_CLUSTER_SIZE = 15

class ScooterPlaceRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<ScooterPlace>
) : DefaultClusterRenderer<ScooterPlace>(context, map, clusterManager) {

    private val scooterIcon: BitmapDescriptor? by lazy {
        bitmapDescriptorFromVector(context, R.drawable.ic_pin_not_selected)
    }

    override fun onBeforeClusterItemRendered(item: ScooterPlace, markerOptions: MarkerOptions) {
        markerOptions
            .title(item.name)
            .position(item.latLng)
            .icon(scooterIcon)
    }

    override fun onClusterItemRendered(clusterItem: ScooterPlace, marker: Marker) {
        marker.tag = clusterItem
    }

    override fun shouldRenderAsCluster(cluster: Cluster<ScooterPlace>?): Boolean {
        return cluster?.size?.compareTo(KEY_MINIMUM_CLUSTER_SIZE) == 1
    }
}
