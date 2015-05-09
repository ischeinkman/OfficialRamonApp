package org.ramonaza.officialramonapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import org.ramonaza.officialramonapp.R;

/**
 * Created by ilanscheinkman on 5/9/15.
 */
public abstract class BaseActivity  extends Activity{

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent settingsIntent=new Intent(this,SettingsActivity.class);
                startActivity(settingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
