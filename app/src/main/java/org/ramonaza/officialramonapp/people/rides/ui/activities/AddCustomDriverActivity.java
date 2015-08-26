package org.ramonaza.officialramonapp.people.rides.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.helpers.ui.activities.BaseActivity;
import org.ramonaza.officialramonapp.people.rides.ui.fragments.AddCustomDriverFragment;

public class AddCustomDriverActivity extends BaseActivity {

    public static final String PRESET_NAME="org.ramonaza.officialramonapp.NAME";
    public static final String PRESET_ADDRESS="org.ramonaza.officialramonapp.ADDRESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        Intent openingIntent=getIntent();
        String pName=openingIntent.getStringExtra(PRESET_NAME);
        String pAddress=openingIntent.getStringExtra(PRESET_ADDRESS);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, AddCustomDriverFragment.newInstance(pName, pAddress))
                    .commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
