package org.ramonaza.officialramonapp.songs.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.ramonaza.officialramonapp.frontpage.ui.activities.FrontalActivity;
import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.ui.fragments.InfoWrapperButtonListFragment;
import org.ramonaza.officialramonapp.songs.backend.SongInfoWrapperGenerator;
import org.ramonaza.officialramonapp.songs.ui.activities.SongDataActivity;

/**
 * The fragment containing the list of songs.
 * Created by ilanscheinkman on 1/9/15.
 */

public class SongListFragment extends InfoWrapperButtonListFragment {

    private static final String EXTRA_CONTRUCTION_INFO="org.ramonaza.officialramonapp.CONSTRUCTION_INFO";
    private static final String EXTRA_LAYER="org.ramonaza.officialramonapp.LAYER_NAME";
    private final String EXTRA_OPENEDPAGE="org.ramonaza.officialramonapp.OPENED_PAGE";

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static int fragLayer; //int to store the position in

    public static SongListFragment newInstance(int sectionNumber) {
        SongListFragment fragment = new SongListFragment();
        Bundle args = new Bundle();
        fragLayer = sectionNumber;
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FrontalActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onButtonClick(InfoWrapper mWrapper) {
        Intent intent=new Intent(getActivity(), SongDataActivity.class);
        intent.putExtra(EXTRA_CONTRUCTION_INFO,mWrapper.getName());
        startActivity(intent);
    }

    @Override
    public InfoWrapper[] generateInfo() {
        return SongInfoWrapperGenerator.allSongs(getActivity());
    }



}