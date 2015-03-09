package org.ramonaza.officialramonapp.uifragments.rides_activity;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ramonaza.officialramonapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriversFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriversFragment extends Fragment {





    public static DriversFragment newInstance() {
        DriversFragment fragment = new DriversFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DriversFragment() {
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
        return inflater.inflate(R.layout.fragment_drivers, container, false);
    }


}
