package com.spence.testinghealthbud;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.lang.reflect.Constructor;

public class Settings extends AppCompatActivity implements OnMapReadyCallback, android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;

    private com.google.android.gms.maps.UiSettings mUiSettings;

    private CheckBox mMyLocationButtonCheckbox;

    private CheckBox mMyLocationLayerCheckbox;

    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;

    private static final int LOCATION_LAYER_PERMISSION_REQUEST_CODE = 2;

    Button btn;

    com.spence.testinghealthbud.PermissionUtils PermUtils = new com.spence.testinghealthbud.PermissionUtils() {

    };

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mLocationPermissionDenied = false;
    //private RestrictionsManager PermissionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_info_contents);

        mMyLocationButtonCheckbox = (CheckBox) findViewById(R.id.mylocationbutton_toggle);
        mMyLocationLayerCheckbox = (CheckBox) findViewById(R.id.mylocationlayer_toggle);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn = (Button) findViewById(R.id.btnSOS);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:82237983"));
                Toast.makeText(com.spence.testinghealthbud.Settings.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;

                }
                startActivity(callIntent);
            }
        });
    }

    /**
     * Returns whether the checkbox with the given id is checked.
     */
    private boolean isChecked(int id) {
        return ((CheckBox) findViewById(id)).isChecked();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mUiSettings = mMap.getUiSettings();

        // Keep the UI Settings state in sync with the checkboxes.
        mUiSettings.setZoomControlsEnabled(isChecked(R.id.zoom_buttons_toggle));
       // mUiSettings.setCompassEnabled(isChecked(R.id.compass_toggle));
        mUiSettings.setMyLocationButtonEnabled(isChecked(R.id.mylocationbutton_toggle));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(isChecked(R.id.mylocationlayer_toggle));
       // mUiSettings.setScrollGesturesEnabled(isChecked(R.id.scroll_toggle));
        mUiSettings.setZoomGesturesEnabled(isChecked(R.id.zoom_gestures_toggle));
      //  mUiSettings.setTiltGesturesEnabled(isChecked(R.id.tilt_toggle));
      //  mUiSettings.setRotateGesturesEnabled(isChecked(R.id.rotate_toggle));
    }

    /**
     * Checks if the map is ready (which depends on whether the Google Play services APK is
     * available. This should be called prior to calling any methods on GoogleMap.
     */
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void setZoomButtonsEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        // Enables/disables the zoom controls (+/- buttons in the bottom-right of the map for LTR
        // locale or bottom-left for RTL locale).
        mUiSettings.setZoomControlsEnabled(((CheckBox) v).isChecked());
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:82237983"));
                Toast.makeText(com.spence.testinghealthbud.Settings.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                    return;
                }
            }
        });
    }

    public void setCompassEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        // Enables/disables the compass (icon in the top-left for LTR locale or top-right for RTL
        // locale that indicates the orientation of the map).
        mUiSettings.setCompassEnabled(((CheckBox) v).isChecked());
    }

    public void setMyLocationButtonEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        // Enables/disables the my location button (this DOES NOT enable/disable the my location
        // dot/chevron on the map). The my location button will never appear if the my location
        // layer is not enabled.
        // First verify that the location permission has been granted.
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mUiSettings.setMyLocationButtonEnabled(mMyLocationButtonCheckbox.isChecked());
        } else {
            // Uncheck the box and request missing location permission.
            mMyLocationButtonCheckbox.setChecked(false);
            requestLocationPermission(MY_LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void requestLocationPermission(int myLocationPermissionRequestCode) {
    }

    public abstract class PermissionUtils extends Activity {

        private Constructor PermissionDeniedDialog;

        public boolean verifyPermissions(int[] grantResults) {
            // At least one result must be checked.
            if(grantResults.length < 1){
                return false;
            }

            // Verify that each required permission has been granted, otherwise return false.
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }

        public void setMyLocationLayerEnabled(View v) {
            if (!checkReady()) {
                return;
            }
            // Enables/disables the my location layer (i.e., the dot/chevron on the map). If enabled, it
            // will also cause the my location button to show (if it is enabled); if disabled, the my
            // location button will never show.
            if (ContextCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(mMyLocationLayerCheckbox.isChecked());
            } else {

                // Uncheck the box and request missing location permission.
                mMyLocationLayerCheckbox.setChecked(false);
                PermUtils.requestPermission(new AppCompatActivity(),LOCATION_LAYER_PERMISSION_REQUEST_CODE,
                        android.Manifest.permission.ACCESS_FINE_LOCATION, false);
            }
        }

        public void setScrollGesturesEnabled(View v) {
            if (!checkReady()) {
                return;
            }
            // Enables/disables scroll gestures (i.e. panning the map).
            mUiSettings.setScrollGesturesEnabled(((CheckBox) v).isChecked());
        }

        public void setZoomGesturesEnabled(View v) {
            if (!checkReady()) {
                return;
            }
            // Enables/disables zoom gestures (i.e., double tap, pinch & stretch).
            mUiSettings.setZoomGesturesEnabled(((CheckBox) v).isChecked());
        }

        public void setTiltGesturesEnabled(View v) {
            if (!checkReady()) {
                return;
            }
            // Enables/disables tilt gestures.
            mUiSettings.setTiltGesturesEnabled(((CheckBox) v).isChecked());
        }

        public void setRotateGesturesEnabled(View v) {
            if (!checkReady()) {
                return;
            }
            // Enables/disables rotate gestures.
            mUiSettings.setRotateGesturesEnabled(((CheckBox) v).isChecked());
        }

        /**
         * Requests the fine location permission. If a rationale with an additional explanation should
         * be shown to the user, displays a dialog that triggers the request.
         */
        public void requestLocationPermission(int requestCode) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Display a dialog with rationale.
                com.spence.testinghealthbud.PermissionUtils.RationaleDialog.newInstance(requestCode, false).show(getSupportFragmentManager(), "dialog");
            } else {
                // Location permission has not been granted yet, request it.
                PermUtils.requestPermission(new AppCompatActivity(), requestCode, android.Manifest.permission.ACCESS_FINE_LOCATION, false);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            if (requestCode == MY_LOCATION_PERMISSION_REQUEST_CODE) {
                // Enable the My Location button if the permission has been granted.
                if (PermUtils.isPermissionGranted(permissions, grantResults, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    mUiSettings.setMyLocationButtonEnabled(true);
                    mMyLocationButtonCheckbox.setChecked(true);
                } else {
                    mLocationPermissionDenied = true;
                }

            } else if (requestCode == LOCATION_LAYER_PERMISSION_REQUEST_CODE) {
                // Enable the My Location layer if the permission has been granted.
                if (PermUtils.isPermissionGranted(permissions, grantResults, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                        mMyLocationLayerCheckbox.setChecked(true);
                        return;
                    }
                } else {
                    mLocationPermissionDenied = true;
                }
            }
        }
    }
    }

