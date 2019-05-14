package com.dsv2019.pvt15.prepapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class ShelterClusterRenderer<T extends ClusterItem> extends DefaultClusterRenderer<T>
{
    private final Context context;

    public ShelterClusterRenderer(Context context, GoogleMap googleMap, ClusterManager<T> clusterManager)
    {
        super(context, googleMap, clusterManager);
        this.context = context;
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster)
    {
        return cluster.getSize() >= 3;
    }

    @Override
    protected void onBeforeClusterItemRendered(T item, MarkerOptions markerOptions)
    {
        super.onBeforeClusterItemRendered(item, markerOptions);




       Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.minibunker);
       markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));

    }
}
