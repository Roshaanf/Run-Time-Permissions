package com.example.roshaan.runtimepermissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String TAG = "abc";
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // checkSinglePermission(new String[]{Manifest.permission.READ_CONTACTS});
        forMultiplePermissions(new String[]{Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA});
    }

    void checkSinglePermission(final String[] permission) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permisssion is granted");

                Toast.makeText(this, "permission already granted", Toast.LENGTH_LONG).show();


            } else {
                Log.i(TAG, "Permisssion is not granted");

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission[0])) {
                    Log.i(TAG, "Permisssion is not granted, hence showing rationale");

                    Snackbar.make(findViewById(android.R.id.content), "Need permission for loading data", Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(MainActivity.this, permission, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                                }
                            }).show();
                } else {
                    Log.i(TAG, "Permisssion being requested for first time");
                    ActivityCompat.requestPermissions(this, permission, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Prmission has been granted", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    void forMultiplePermissions(String[] permissions) {

        String[] permissionsRequired;
        permissionsRequired = checkPermissionsNeeded(permissions);

        askPermissions(permissionsRequired);


    }


    String[] checkPermissionsNeeded(String[] permissions) {

        ArrayList<String> permisionsNeeded = new ArrayList();
        for (String permission : permissions) {


            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, permission + " Permisssion already granted");

                Toast.makeText(this, "permission already granted", Toast.LENGTH_LONG).show();

            } else {
                permisionsNeeded.add(permission);
            }
        }
        return permisionsNeeded.toArray(new String[permisionsNeeded.size()]);
    }

    void askPermissions(final String[] permissionRequired) {

        if (permissionRequired.length > 0) {
            if (!forTheFirstTime(permissionRequired)) {
                //this code will run if permissions are already beign asked and denied
                Log.i(TAG, "Permisssion is not granted, hence showing rationale");

                Snackbar.make(findViewById(android.R.id.content), "Need permission for loading data", Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //asking all required permissions
                                ActivityCompat.requestPermissions(MainActivity.this, permissionRequired, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        }).show();
            } else {
                //asking all required permissions for the first time
                Log.i(TAG, "Permisssion being requested for first time");
                ActivityCompat.requestPermissions(this, permissionRequired, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    Boolean forTheFirstTime(String permissions[]) {

        for (String permission : permissions)
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {

            //if permission is already being asked and denied
                return false;
            }

        return true;

    }
}
