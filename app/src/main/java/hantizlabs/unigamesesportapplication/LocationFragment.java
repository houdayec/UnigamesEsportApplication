package hantizlabs.unigamesesportapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class LocationFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    UiSettings uiSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_layout, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Display and pin the position of the university on the map loading
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                //latitude and longitude for Metropolia
                LatLng location = new LatLng(60.2208265, 24.8046457);

                // create marker
                MarkerOptions marker = new MarkerOptions().position(
                        location).title(getString(R.string.location));

                // adding marker
                googleMap.addMarker(marker);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(location).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));

                uiSettings = googleMap.getUiSettings();

                //Allow the user to zoom the map
                uiSettings.setZoomGesturesEnabled(true);
            }
        });

        //Implementation of the floating button and launch navigation when get clicked
        FloatingActionButton fabNavigate = (FloatingActionButton) rootView.findViewById(R.id.fabNavigate);
        fabNavigate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(
                        "http://maps.google.com/maps?daddr=60.2208265, 24.8046457"));
                startActivity(intent);
            }
        });

        return rootView;
    }

    //Modifying the fragment title
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null)
                activity.getSupportActionBar().setTitle(getString(R.string.action_location));
        }
    }


    //Handle different fragment states and adapt the map
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}