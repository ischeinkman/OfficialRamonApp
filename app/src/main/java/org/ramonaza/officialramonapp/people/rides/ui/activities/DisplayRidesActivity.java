package org.ramonaza.officialramonapp.people.rides.ui.activities;

import android.os.Bundle;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.helpers.ui.activities.BaseActivity;
import org.ramonaza.officialramonapp.people.rides.ui.fragments.DisplayRidesFragment;

public class DisplayRidesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_rides);
        if(savedInstanceState ==null){
            getFragmentManager().beginTransaction()
                    .add(R.id.container, DisplayRidesFragment.newInstance())
                    .commit();
        }
    }

}
