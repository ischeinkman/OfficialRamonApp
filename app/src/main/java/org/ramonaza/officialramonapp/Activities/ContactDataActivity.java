package org.ramonaza.officialramonapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.UIFragments.GeneralContactFragment;
import org.ramonaza.officialramonapp.dataFiles.ContactInfoWrapper;

public class ContactDataActivity extends Activity {

    private static final String EXTRA_CONTRUCTION_INFO="org.ramonaza.officialramonapp.CONSTRUCTION_INFO";
    private static final String EXTRA_LAYER="org.ramonaza.officialramonapp.LAYER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layer2);
        Intent intent=getIntent();
        FragmentManager fragmentManager = getFragmentManager();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if(intent.getStringExtra(EXTRA_LAYER).equals("Contact List")){
            ContactInfoWrapper contactInfoWrapper=new ContactInfoWrapper(getIntent().getStringArrayExtra(EXTRA_CONTRUCTION_INFO));
            FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.container, GeneralContactFragment.newInstance(1,contactInfoWrapper))
                    .addToBackStack(null);
            transaction.commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layer2, menu);
        Intent intent=getIntent();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (id){
            case android.R.id.home:

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
