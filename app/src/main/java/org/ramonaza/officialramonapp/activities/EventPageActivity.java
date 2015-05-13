package org.ramonaza.officialramonapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.datafiles.EventInfoWrapper;
import org.ramonaza.officialramonapp.uifragments.GeneralEventFragment;

public class EventPageActivity extends BaseActivity {
    private static final String EVENT_DATA = "org.ramonaza.officialramonapp.EVENT_DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        Intent rIntent=getIntent();
        EventInfoWrapper event=rIntent.getParcelableExtra(EVENT_DATA);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, GeneralEventFragment.newInstance(event))
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
