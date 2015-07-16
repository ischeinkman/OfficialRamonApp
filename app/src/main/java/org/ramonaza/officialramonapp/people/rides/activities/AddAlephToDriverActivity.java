package org.ramonaza.officialramonapp.people.rides.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.helpers.activities.BaseActivity;
import org.ramonaza.officialramonapp.people.rides.fragments.AddAlephToDriverFragment;

public class AddAlephToDriverActivity extends BaseActivity {

    private int driverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        driverId=intent.getIntExtra("DriverId",0);
        setContentView(R.layout.activity_add_aleph_to_driver);
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, AddAlephToDriverFragment.newInstance(driverId)).commit();
    }
}
