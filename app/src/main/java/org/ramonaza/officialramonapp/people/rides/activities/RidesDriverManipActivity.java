package org.ramonaza.officialramonapp.people.rides.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.helpers.activities.BaseActivity;
import org.ramonaza.officialramonapp.people.rides.fragments.RidesDriverManipFragment;

public class RidesDriverManipActivity extends BaseActivity {

    private int driverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_driver_manip);
        Intent intent=getIntent();
        driverId=intent.getIntExtra("DriverId",0);
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, RidesDriverManipFragment.newInstance(driverId)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_manip, menu);
        return true;
    }
}
