package org.ramonaza.officialramonapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapper;
import org.ramonaza.officialramonapp.uifragments.GeneralContactFragment;

public class ContactDataActivity extends Activity {

    private static final String EXTRA_CONTRUCTION_INFO="org.ramonaza.officialramonapp.CONSTRUCTION_INFO";
    private static final String EXTRA_LAYER="org.ramonaza.officialramonapp.LAYER_NAME";
    private final String EXTRA_OPENEDPAGE="org.ramonaza.officialramonapp.OPENED_PAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Intent intent=getIntent();
        FragmentManager fragmentManager = getFragmentManager();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ContactInfoWrapper contactInfoWrapper=new ContactInfoWrapper(intent.getStringArrayExtra(EXTRA_CONTRUCTION_INFO));
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.container, GeneralContactFragment.newInstance(1,contactInfoWrapper));
        transaction.commit();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layer2, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent=new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        switch (id){
            case android.R.id.home:
                Intent bacIntent=NavUtils.getParentActivityIntent(this);
                bacIntent.putExtra(EXTRA_OPENEDPAGE,3);
                startActivity(bacIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
