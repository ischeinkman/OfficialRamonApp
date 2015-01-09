package org.ramonaza.officialramonapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * General Song Text Class.
 * Created by Ilan Scheinkman on 1/9/15.
 */
public class GeneralSongFrag extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static GeneralSongFrag newInstance(int sectionNumber, String songTitle) {
        GeneralSongFrag fragment = new GeneralSongFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString("songTitle",songTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public GeneralSongFrag() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ActionBar actionBar = getActivity().getActionBar();
        String songName=getArguments().getString("songTitle");
        actionBar.setTitle(songName);
        View rootView = inflater.inflate(R.layout.fragment_general_song_page, container, false);
        TextView songText= (TextView) rootView.findViewById(R.id.songTextView);
        songText.setText(getStringResourceByName(songName.toLowerCase().replace(" ","")+"text"));
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FrontPage) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private String getStringResourceByName(String aString)
    {
        String packageName = "org.ramonaza.officialramonapp";
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }

}