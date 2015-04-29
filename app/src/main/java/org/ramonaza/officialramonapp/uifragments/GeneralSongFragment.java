package org.ramonaza.officialramonapp.uifragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.datafiles.SongInfoWrapper;

/**
 * General Song Text Class.
 * Created by Ilan Scheinkman on 1/9/15.
 */
public class GeneralSongFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private SongInfoWrapper mySong;

    public void setSong(SongInfoWrapper songInfoWrapper){
        this.mySong=songInfoWrapper;
    }

    public static GeneralSongFragment newInstance(int sectionNumber, SongInfoWrapper song) {
        GeneralSongFragment fragment = new GeneralSongFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setSong(song);
        fragment.setArguments(args);
        return fragment;
    }

    public GeneralSongFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle(mySong.getName());
        View rootView = inflater.inflate(R.layout.fragment_song_data, container, false);
        TextView songText = (TextView) rootView.findViewById(R.id.songTextView);
        songText.setText(mySong.getLyrics());
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

}