package org.ramonaza.officialramonapp.songs.ui.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.frontpage.ui.activities.FrontalActivity;
import org.ramonaza.officialramonapp.helpers.ui.activities.BaseActivity;
import org.ramonaza.officialramonapp.songs.backend.SongInfoWrapperGenerator;
import org.ramonaza.officialramonapp.songs.ui.fragments.GeneralSongFragment;

public class SongDataActivity extends BaseActivity {

    public static final String EXTRA_CONTRUCTION_INFO="org.ramonaza.officialramonapp.CONSTRUCTION_INFO";
    private static final String EXTRA_LAYER="org.ramonaza.officialramonapp.LAYER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_data);
        Intent intent=getIntent();
        String songName=intent.getStringExtra(EXTRA_CONTRUCTION_INFO);
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction().replace(R.id.container, GeneralSongFragment.newInstance(SongInfoWrapperGenerator.fromName(songName, this)));
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                Intent bacIntent= NavUtils.getParentActivityIntent(this);
                bacIntent.putExtra(FrontalActivity.EXTRA_OPENEDPAGE,2);
                startActivity(bacIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
