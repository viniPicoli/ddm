package com.example.ddm.ui.gallery;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private LocationManager locationManager;
    private double lat;
    private double lon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setScrollGesturesEnabled(true);

            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);

        } catch (SecurityException e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        EditText editTextCidade = getActivity().findViewById(R.id.editTextLocalCidade);
        editTextCidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) {

                EditText localCep = getActivity().findViewById(R.id.editTextLocalCep);
                EditText localRua = getActivity().findViewById(R.id.editTextLocalRua);
                EditText localBairro = getActivity().findViewById(R.id.editTextLocalBairro);
                EditText localCidade = getActivity().findViewById(R.id.editTextLocalCidade);
                Spinner localUF = (Spinner) getActivity().findViewById(R.id.spinnerLocalUf);
                TextView localLatitude = getActivity().findViewById(R.id.textViewLocalLatitudeValue);
                TextView localLongitude = getActivity().findViewById(R.id.textViewLocalLongitudeValue);

                String ad = localCep.getText().toString() + "," + localRua.getText().toString() + "," + localBairro.getText().toString() +
                        "," + localCidade.getText().toString() + "," + localUF.getSelectedItem().toString();

                LatLng latLng = getLocationFromAddress(ad);

                localLatitude.setText(Double.toString(latLng.latitude));
                localLongitude.setText(Double.toString(latLng.longitude));

                //Put marker on map on that LatLng
                Marker srchMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Local"));

                //Animate and Zoon on that map location
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        });

    }

    public LatLng getLocationFromAddress(String strAddress) {
        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(getContext());
        List<Address> address;

        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress,5);

            //check for null
            if (address == null) {
                return null;
            }

            //Lets take first possibility from the all possibilities.
            Address location=address.get(0);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            return latLng;

        } catch (IOException e) {
            return null;
        }
    }

}