package org.ramonaza.officialramonapp.uifragments.rides_activity;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ramonaza.officialramonapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlephsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlephsFragment extends Fragment {




    public static AlephsFragment newInstance() {
        AlephsFragment fragment = new AlephsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AlephsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rides_alephs, container, false);
    }


}
