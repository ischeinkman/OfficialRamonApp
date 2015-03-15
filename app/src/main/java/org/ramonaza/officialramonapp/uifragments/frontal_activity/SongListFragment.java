package org.ramonaza.officialramonapp.uifragments.frontal_activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.activities.FrontalActivity;
import org.ramonaza.officialramonapp.activities.SongDataActivity;
import org.ramonaza.officialramonapp.uifragments.GeneralSongFragment;

/**
 * The fragment containing the list of songs.
 * Created by ilanscheinkman on 1/9/15.
 */

public class SongListFragment extends Fragment {

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

    public SongListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle("Songs");
        View rootView = inflater.inflate(R.layout.fragment_song_list, container, false);
        Button upYouMenButton = (Button) rootView.findViewById(R.id.upYouMenButton);
        upYouMenButton.setOnClickListener(new View.OnClickListener() { //Confused by this syntax? So am I.
            public void onClick(View v) {  //We appear to be defining an instance method for a nameless OnClickListener
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.container, GeneralSongFragment.newInstance(fragLayer + 1, "Up You Men"))
                        .addToBackStack(null); //At least this is clear. Begin the transaction, replace the frag with the new one, and then allow it to be back buttoned in a single line.
                transaction.commit();
            }
        });
        Button forTommorowAndTodayButton = (Button) rootView.findViewById(R.id.forTommorowAndTodayButton);
        forTommorowAndTodayButton.setOnClickListener(new SongButtonListener().setSong("For Tomorow And Today"));
        Button worldOfAZAButton = (Button) rootView.findViewById(R.id.worldOfAZAButton);
        worldOfAZAButton.setOnClickListener(new SongButtonListener().setSong("World of AZA"));
        Button azaAllTheWayButton=(Button) rootView.findViewById(R.id.azaAllTheWayButton);
        azaAllTheWayButton.setOnClickListener(new SongButtonListener().setSong("AZA All The Way"));
        Button proudToBeAlephButton=(Button) rootView.findViewById(R.id.proudToBeAnAlephButton);
        proudToBeAlephButton.setOnClickListener(new SongButtonListener().setSong("Proud To Be An Aleph"));
        Button elHamaayanButton=(Button) rootView.findViewById(R.id.elHaMaayanButton);
        elHamaayanButton.setOnClickListener(new SongButtonListener().setSong("El HaMaayan"));
        Button neverTooManyButton=(Button) rootView.findViewById(R.id.neverTooManyButton);
        neverTooManyButton.setOnClickListener(new SongButtonListener().setSong("Never Too Many"));
        Button gentlemenButton=(Button) rootView.findViewById(R.id.gentlemenButton);
        gentlemenButton.setOnClickListener(new SongButtonListener().setSong("Gentlemen"));
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FrontalActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
    public class SongButtonListener implements View.OnClickListener{
        String songName;
        public SongButtonListener setSong(String song){
            this.songName=song;
            return this;
        }
        public void onClick(View v) {
            Intent intent=new Intent(getActivity(), SongDataActivity.class);
            intent.putExtra(EXTRA_CONTRUCTION_INFO,songName);
            startActivity(intent);
        }
    }

}