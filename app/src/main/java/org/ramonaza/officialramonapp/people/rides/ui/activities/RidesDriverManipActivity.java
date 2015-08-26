package org.ramonaza.officialramonapp.people.rides.ui.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.helpers.ui.activities.BaseActivity;
import org.ramonaza.officialramonapp.people.rides.ui.fragments.RidesDriverManipFragment;

public class RidesDriverManipActivity extends BaseActivity {

    private int driverId;
    public static final String EXTRA_DRIVERID="org.ramonaza.officialramonapp.DRIVER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        Intent intent=getIntent();
        driverId=intent.getIntExtra(EXTRA_DRIVERID,0);
        if(driverId == 0 && savedInstanceState != null) driverId=savedInstanceState.getInt(EXTRA_DRIVERID, 0);
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, RidesDriverManipFragment.newInstance(driverId)).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_DRIVERID, driverId);
    }
}
